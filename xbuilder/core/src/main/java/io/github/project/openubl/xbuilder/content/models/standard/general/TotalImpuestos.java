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
public class TotalImpuestos {

    @Schema(minimum = "0")
    private BigDecimal total;

    @Schema(minimum = "0")
    private BigDecimal ivapImporte;

    @Schema(minimum = "0")
    private BigDecimal ivapBaseImponible;

    @Schema(minimum = "0")
    private BigDecimal exportacionImporte;

    @Schema(minimum = "0")
    private BigDecimal exportacionBaseImponible;

    @Schema(minimum = "0")
    private BigDecimal gravadoImporte;

    @Schema(minimum = "0")
    private BigDecimal gravadoBaseImponible;

    @Schema(minimum = "0")
    private BigDecimal inafectoImporte;

    @Schema(minimum = "0")
    private BigDecimal inafectoBaseImponible;

    @Schema(minimum = "0")
    private BigDecimal exoneradoImporte;

    @Schema(minimum = "0")
    private BigDecimal exoneradoBaseImponible;

    @Schema(minimum = "0")
    private BigDecimal gratuitoImporte;

    @Schema(minimum = "0")
    private BigDecimal gratuitoBaseImponible;

    @Schema(minimum = "0")
    private BigDecimal icbImporte;

    @Schema(minimum = "0")
    private BigDecimal iscImporte;

    @Schema(minimum = "0")
    private BigDecimal iscBaseImponible;
}
