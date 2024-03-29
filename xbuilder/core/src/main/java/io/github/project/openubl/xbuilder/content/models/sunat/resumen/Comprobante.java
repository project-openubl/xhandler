/*
 * Copyright 2019 Project OpenUBL, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License - 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.project.openubl.xbuilder.content.models.sunat.resumen;

import io.github.project.openubl.xbuilder.content.models.common.Cliente;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comprobante {

    @Schema(requiredMode = Schema.RequiredMode.AUTO, description = "Moneda del comprobante declarado")
    private String moneda;
    
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Catalogo 01")
    private String tipoComprobante;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String serieNumero;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Cliente cliente;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private ComprobanteValorVenta valorVenta;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private ComprobanteImpuestos impuestos;

    private ComprobanteAfectado comprobanteAfectado;
}
