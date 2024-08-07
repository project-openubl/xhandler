package io.github.project.openubl.xbuilder.content.models.standard.general;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoVentaDetalle {

    @Schema(description = "Descripcion del bien o servicio", requiredMode = Schema.RequiredMode.REQUIRED)
    private String descripcion;

    private String unidadMedida;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, minimum = "0", exclusiveMinimum = true)
    private BigDecimal cantidad;

    @Schema(description = "Precio sin incluir impuestos", minimum = "0")
    private BigDecimal precio;

    @Schema(description = "Precio incluyendo impuestos")
    private boolean precioConImpuestos;

    @Schema(minimum = "0")
    private BigDecimal precioReferencia;

    @Schema(description = "Catalog 16")
    private String precioReferenciaTipo;

    // Impuestos
    @Schema(description = "Ejemplo: 0.18", minimum = "0", maximum = "1")
    private BigDecimal tasaIgv;

    @Schema(description = "Monto total de IGV", minimum = "0")
    private BigDecimal igv;

    @Schema(minimum = "0")
    private BigDecimal igvBaseImponible;

    @Schema(description = "Catalogo 07")
    private String igvTipo;

    @Schema(minimum = "0")
    private BigDecimal tasaIcb;

    @Schema(minimum = "0")
    private BigDecimal icb;

    @Schema(description = "'true' si ICB is aplicado a este bien o servicio")
    private boolean icbAplica;

    @Schema(description = "Ejemplo: 0.17", minimum = "0", maximum = "1")
    private BigDecimal tasaIsc;

    @Schema(description = "Monto total de ISC", minimum = "0")
    private BigDecimal isc;

    @Schema(minimum = "0")
    private BigDecimal iscBaseImponible;

    @Schema(description = "Catalogo 08")
    private String iscTipo;

    // Totales
    @Schema(minimum = "0")
    private BigDecimal totalImpuestos;
}
