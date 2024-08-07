package io.github.project.openubl.xsender.camel.routes;

import io.github.project.openubl.xsender.Constants;
import io.github.project.openubl.xsender.models.Metadata;
import io.github.project.openubl.xsender.models.Status;
import io.github.project.openubl.xsender.models.SunatResponse;
import io.github.project.openubl.xsender.models.rest.ResponseAccessTokenSuccessDto;
import io.github.project.openubl.xsender.models.rest.ResponseDocumentErrorDto;
import io.github.project.openubl.xsender.models.rest.ResponseDocumentSuccessDto;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.camel.component.http.HttpConstants;
import org.apache.camel.component.http.HttpMethods;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.util.URISupport;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.transport.http.HTTPException;

import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SunatRouteBuilder extends RouteBuilder {

    @Override
    public void configure() {
        from(Constants.XSENDER_BILL_SERVICE_URI)
                .id("xsender-billService")
                .choice()
                    // SOAP
                    .when(header(CxfConstants.OPERATION_NAME).isNotNull())
                        .to("cxf://bean:cxfBillServiceEndpoint?dataFormat=POJO")
                        .process(new SoapSunatResponseProcessor())
                    .endChoice()
                    // REST
                    .when(header(HttpConstants.HTTP_METHOD).isNotNull())
                        .marshal().json(JsonLibrary.Jackson)
                        .to("https://api-cpe.sunat.gob.pe")
                        .unmarshal(new JacksonDataFormat(ResponseDocumentSuccessDto.class))
                        .process(new RestSunatResponseProcessor())
                    .endChoice()
                    // Otherwise
                    .otherwise()
                        .throwException(new RuntimeException("Not supported protocol identified"))
                    .endChoice()
                .end()

                // SOAP Exception
                .onException(SoapFault.class)
                    .continued(true)
                    .process(new SoapSunatErrorResponseProcessor())
                .end()
                .onException(HTTPException.class)
                    .continued(true)
                    .maximumRedeliveries(1)
                    .useOriginalMessage()
                    .process(exchange -> {
                        Throwable throwable = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);

                        SunatResponse sunatResponse = SunatResponse.builder()
                                .status(Status.UNKNOWN)
                                .metadata(Metadata.builder()
                                        .notes(Collections.emptyList())
                                        .description(throwable.getMessage())
                                        .build())
                                .build();

                        exchange.getIn().setBody(sunatResponse);
                    })
                .end()

                // REST exception
                .onException(HttpOperationFailedException.class)
                    .continued(exchange -> {
                        HttpOperationFailedException httpException = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, HttpOperationFailedException.class);
                        String contentType = httpException.getResponseHeaders().getOrDefault("Content-Type", "");

                        boolean isResponseJson = Objects.equals(contentType, "application/json");
                        if (isResponseJson) {
                            exchange.getIn().setBody(httpException.getResponseBody());
                        }

                        exchange.getIn().setHeader("HttpResponseHeader_ContentType", contentType);
                        return isResponseJson;
                    })
                    .choice()
                        .when(header("HttpResponseHeader_ContentType").isEqualTo("application/json"))
                            .unmarshal(new JacksonDataFormat(ResponseDocumentErrorDto.class))
                            .process(new RestSunatErrorResponseProcessor())
                        .endChoice()
                        .otherwise()
                            .log(LoggingLevel.WARN, "Response from server is not JSON, something went wrong while connecting to the remote server")
                        .endChoice()
                    .end()
                .end();

        from(Constants.XSENDER_BILL_CONSULT_SERVICE_URI)
                .id("xsender-billConsultService")
                .to("cxf://bean:cxfBillConsultServiceEndpoint?dataFormat=POJO");

        from(Constants.XSENDER_BILL_VALID_SERVICE_URI)
                .id("xsender-billValidService")
                .to("cxf://bean:cxfBillValidServiceEndpoint?dataFormat=POJO");

        // Requires a List as body.
        // Where the first element (List(0)) is the prev AccessTokenDto (NULL if there is no prev value)
        // Second element of the list (List(1)) is the 'x-www-form-urlencoded' form as a Map object
        from(Constants.XSENDER_CREDENTIALS_API_URI)
                .id("xsender-credentialsApi")
                .choice()
                    // Refresh token
                    .when(exchange -> {
                        List<?> body = exchange.getIn().getBody(List.class);
                        ResponseAccessTokenSuccessDto prevToken = (ResponseAccessTokenSuccessDto) body.get(0);

                        ZonedDateTime prevTokenCreatedIn = prevToken.getCreated_in();
                        ZonedDateTime expirationDate = prevTokenCreatedIn.plusSeconds(prevToken.getExpires_in())
                                .plusSeconds(30); // adding seconds to give time to the next operation to perform

                        return ZonedDateTime.now().compareTo(expirationDate) >= 0;
                    })
                        .setHeader(HttpConstants.HTTP_METHOD, constant(HttpMethods.POST))
                        .setHeader(HttpConstants.CONTENT_TYPE, constant("application/x-www-form-urlencoded"))
                        .setBody(exchange -> {
                            List<?> body = exchange.getIn().getBody(List.class);
                            Map<String, Object> map = (Map<String, Object>) body.get(1);
                            return URISupport.createQueryString(map);
                        })
                        .to("https://api-seguridad.sunat.gob.pe")
                        .unmarshal(new JacksonDataFormat(ResponseAccessTokenSuccessDto.class))
                        .process(exchange -> {
                            ResponseAccessTokenSuccessDto response = exchange.getIn().getBody(ResponseAccessTokenSuccessDto.class);
                            response.setCreated_in(ZonedDateTime.now());
                        })
                    .endChoice()
                    // Reuse previous token
                    .otherwise()
                        .setBody(exchange -> {
                            List<?> body = exchange.getIn().getBody(List.class);
                            return (ResponseAccessTokenSuccessDto) body.get(0);
                        })
                    .endChoice()
                .end();
    }
}
