package e2e.renderer.perception;

import e2e.AbstractTest;
import io.github.project.openubl.xbuilder.content.catalogs.Catalog1;
import io.github.project.openubl.xbuilder.content.catalogs.Catalog22;
import io.github.project.openubl.xbuilder.content.catalogs.Catalog6;
import io.github.project.openubl.xbuilder.content.models.common.Cliente;
import io.github.project.openubl.xbuilder.content.models.common.Proveedor;
import io.github.project.openubl.xbuilder.content.models.sunat.percepcionretencion.ComprobanteAfectado;
import io.github.project.openubl.xbuilder.content.models.sunat.percepcionretencion.PercepcionRetencionOperacion;
import io.github.project.openubl.xbuilder.content.models.sunat.percepcionretencion.Perception;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PerceptionTest extends AbstractTest {

    @Test
    public void testSimplePerception() throws Exception {
        // Given
        Perception input = Perception.builder()
                .serie("P001")
                .numero(1)
                .fechaEmision(LocalDate.of(2022, 01, 31))
                .proveedor(Proveedor.builder()
                        .ruc("12345678912")
                        .razonSocial("Softgreen S.A.C.")
                        .build()
                )
                .cliente(Cliente.builder()
                        .nombre("Carlos Feria")
                        .numeroDocumentoIdentidad("12121212121")
                        .tipoDocumentoIdentidad(Catalog6.RUC.getCode())
                        .build()
                )
                .importeTotalPercibido(new BigDecimal("10"))
                .importeTotalCobrado(new BigDecimal("210"))
                .tipoRegimen(Catalog22.VENTA_INTERNA.getCode())
                .tipoRegimenPorcentaje(Catalog22.VENTA_INTERNA.getPercent()) //
                .operacion(PercepcionRetencionOperacion.builder()
                        .numeroOperacion(1)
                        .fechaOperacion(LocalDate.of(2022, 01, 31))
                        .importeOperacion(new BigDecimal("100"))
                        .comprobante(ComprobanteAfectado.builder()
                                .tipoComprobante(Catalog1.FACTURA.getCode())
                                .serieNumero("F001-1")
                                .fechaEmision(LocalDate.of(2022, 01, 31))
                                .importeTotal(new BigDecimal("200"))
                                .moneda("PEN")
                                .build()
                        )
                        .build()
                )
                .build();

        assertInput(input, "perception_simple.xml");
    }

}
