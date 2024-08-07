package io.github.project.openubl.xbuilder.enricher.kie.rules.enrich.body.anticipo;

import io.github.project.openubl.xbuilder.content.catalogs.Catalog;
import io.github.project.openubl.xbuilder.content.catalogs.Catalog12;
import io.github.project.openubl.xbuilder.content.models.standard.general.Anticipo;
import io.github.project.openubl.xbuilder.content.models.utils.UBLRegex;
import io.github.project.openubl.xbuilder.enricher.kie.AbstractBodyRule;
import io.github.project.openubl.xbuilder.enricher.kie.RulePhase;

import java.util.Optional;
import java.util.function.Consumer;

import static io.github.project.openubl.xbuilder.enricher.kie.rules.utils.Helpers.isAnticipo;
import static io.github.project.openubl.xbuilder.enricher.kie.rules.utils.Helpers.whenAnticipo;

/**
 * Rule for: {@link Anticipo#comprobanteTipo}
 *
 * @author <a href="mailto:carlosthe19916@gmail.com">Carlos Feria</a>
 */
@RulePhase(type = RulePhase.PhaseType.ENRICH)
public class ComprobanteTipoAnticipoRule extends AbstractBodyRule {

    @Override
    public boolean test(Object object) {
        return isAnticipo.test(object);
    }

    @Override
    public void modify(Object object) {
        Consumer<Anticipo> consumer = anticipo -> {
            String comprobanteTipo = null;
            if (anticipo.getComprobanteTipo() == null) {
                if (UBLRegex.FACTURA_SERIE_REGEX.matcher(anticipo.getComprobanteSerieNumero()).matches()) {
                    comprobanteTipo = Catalog12.FACTURA_EMITIDA_POR_ANTICIPOS.getCode();
                } else if (UBLRegex.BOLETA_SERIE_REGEX.matcher(anticipo.getComprobanteSerieNumero()).matches()) {
                    comprobanteTipo = Catalog12.BOLETA_DE_VENTA_EMITIDA_POR_ANTICIPOS.getCode();
                }
            } else {
                Optional<Catalog12> catalog12 = Catalog.valueOfCode(Catalog12.class, anticipo.getComprobanteTipo());
                if (catalog12.isPresent()) {
                    comprobanteTipo = catalog12.get().getCode();
                }
            }

            anticipo.setComprobanteTipo(comprobanteTipo);
        };
        whenAnticipo.apply(object).ifPresent(consumer);
    }
}
