package io.github.project.openubl.xbuilder.enricher.kie.rules.enrich.body.detalle;

import io.github.project.openubl.xbuilder.content.catalogs.Catalog;
import io.github.project.openubl.xbuilder.content.catalogs.Catalog16;
import io.github.project.openubl.xbuilder.content.catalogs.Catalog7;
import io.github.project.openubl.xbuilder.content.models.standard.general.DocumentoVentaDetalle;
import io.github.project.openubl.xbuilder.enricher.kie.AbstractBodyRule;
import io.github.project.openubl.xbuilder.enricher.kie.RulePhase;

import java.util.function.Consumer;

import static io.github.project.openubl.xbuilder.enricher.kie.rules.utils.Helpers.isSalesDocumentItem;
import static io.github.project.openubl.xbuilder.enricher.kie.rules.utils.Helpers.whenSalesDocumentItem;

@RulePhase(type = RulePhase.PhaseType.ENRICH)
public class PrecioDeReferenciaTipoRule extends AbstractBodyRule {

    @Override
    public boolean test(Object object) {
        return (
                isSalesDocumentItem.test(object) &&
                        whenSalesDocumentItem.apply(object).map(documento -> documento.getIgvTipo() != null).orElse(false)
        );
    }

    @Override
    public void modify(Object object) {
        Consumer<DocumentoVentaDetalle> consumer = detalle -> {
            Catalog7 catalog7 = Catalog
                    .valueOfCode(Catalog7.class, detalle.getIgvTipo())
                    .orElseThrow(Catalog.invalidCatalogValue);
            Catalog16 catalog16 = catalog7.isOperacionOnerosa()
                    ? Catalog16.PRECIO_UNITARIO_INCLUYE_IGV
                    : Catalog16.VALOR_REFERENCIAL_UNITARIO_EN_OPERACIONES_NO_ONEROSAS;

            detalle.setPrecioReferenciaTipo(catalog16.getCode());
        };
        whenSalesDocumentItem.apply(object).ifPresent(consumer);
    }
}
