package e2e.renderer.invoice;

import e2e.AbstractTest;
import io.github.project.openubl.xbuilder.content.catalogs.Catalog6;
import io.github.project.openubl.xbuilder.content.catalogs.Catalog7;
import io.github.project.openubl.xbuilder.content.models.common.Cliente;
import io.github.project.openubl.xbuilder.content.models.common.Proveedor;
import io.github.project.openubl.xbuilder.content.models.standard.general.DocumentoVentaDetalle;
import io.github.project.openubl.xbuilder.content.models.standard.general.Invoice;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;

public class InvoiceTipoIgvTest extends AbstractTest {

    @ParameterizedTest
    @EnumSource(Catalog7.class)
    public void testInvoice(Catalog7 tipoIgv) throws Exception {
        // Given
        Invoice input = Invoice.builder()
                .serie("F001")
                .numero(1)
                .proveedor(Proveedor.builder()
                        .ruc("12345678912")
                        .razonSocial("Softgreen S.A.C.")
                        .build()
                )
                .cliente(Cliente.builder()
                        .nombre("Carlos Feria")
                        .numeroDocumentoIdentidad("12121212121")
                        .tipoDocumentoIdentidad(Catalog6.RUC.toString())
                        .build()
                )
                .detalle(DocumentoVentaDetalle.builder()
                        .descripcion("Item1")
                        .cantidad(new BigDecimal("1"))
                        .precio(new BigDecimal("100"))
                        .igvTipo(tipoIgv.getCode())
                        .build()
                )
                .build();

        assertInput(input, "invoice_pu_" + tipoIgv + ".xml");
    }

    @ParameterizedTest
    @EnumSource(Catalog7.class)
    public void testInvoiceWithPrecionConImpuestos(Catalog7 tipoIgv) throws Exception {
        // Given
        Invoice input = Invoice.builder()
                .serie("F001")
                .numero(1)
                .proveedor(Proveedor.builder()
                        .ruc("12345678912")
                        .razonSocial("Softgreen S.A.C.")
                        .build()
                )
                .cliente(Cliente.builder()
                        .nombre("Carlos Feria")
                        .numeroDocumentoIdentidad("12121212121")
                        .tipoDocumentoIdentidad(Catalog6.RUC.toString())
                        .build()
                )
                .detalle(DocumentoVentaDetalle.builder()
                        .descripcion("Item1")
                        .cantidad(new BigDecimal("1"))
                        .precio(new BigDecimal("118"))
                        .precioConImpuestos(true)
                        .igvTipo(tipoIgv.getCode())
                        .build()
                )
                .build();

        assertInput(input, "invoice_pr_" + tipoIgv + ".xml");
    }

    @ParameterizedTest
    @EnumSource(Catalog7.class)
    public void testInvoiceWithPrecionConImpuestosAndIcb(Catalog7 tipoIgv) throws Exception {
        // Given
        Invoice input = Invoice.builder()
                .serie("F001")
                .numero(1)
                .proveedor(Proveedor.builder()
                        .ruc("12345678912")
                        .razonSocial("Softgreen S.A.C.")
                        .build()
                )
                .cliente(Cliente.builder()
                        .nombre("Carlos Feria")
                        .numeroDocumentoIdentidad("12121212121")
                        .tipoDocumentoIdentidad(Catalog6.RUC.toString())
                        .build()
                )
                .detalle(DocumentoVentaDetalle.builder()
                        .descripcion("Item1")
                        .cantidad(new BigDecimal("1"))
                        .precio(new BigDecimal("118"))
                        .precioConImpuestos(true)
                        .igvTipo(tipoIgv.getCode())
                        .icbAplica(true)
                        .build()
                )
                .build();

        assertInput(input, "invoice_pr_icb_" + tipoIgv + ".xml");
    }

    @ParameterizedTest
    @EnumSource(Catalog7.class)
    public void testInvoiceWithPrecionConImpuestosAndIcbAndIsc(Catalog7 tipoIgv) throws Exception {
        // ISC and IVAP can not be put together: Sunat validation
        if (tipoIgv.equals(Catalog7.GRAVADO_IVAP)) {
            return;
        }

        // Given
        Invoice input = Invoice.builder()
                .serie("F001")
                .numero(1)
                .proveedor(Proveedor.builder()
                        .ruc("12345678912")
                        .razonSocial("Softgreen S.A.C.")
                        .build()
                )
                .cliente(Cliente.builder()
                        .nombre("Carlos Feria")
                        .numeroDocumentoIdentidad("12121212121")
                        .tipoDocumentoIdentidad(Catalog6.RUC.toString())
                        .build()
                )
                .detalle(DocumentoVentaDetalle.builder()
                        .descripcion("Item1")
                        .cantidad(new BigDecimal("1"))
                        .precio(new BigDecimal("100"))
                        .precioConImpuestos(true)
                        .igvTipo(tipoIgv.getCode())
                        .icbAplica(true)
                        .tasaIsc(new BigDecimal("0.10"))
                        .build()
                )
                .build();

        assertInput(input, "invoice_pr_icb_isc_" + tipoIgv + ".xml");
    }
}
