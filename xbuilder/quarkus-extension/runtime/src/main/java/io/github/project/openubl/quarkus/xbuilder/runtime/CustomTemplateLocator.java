package io.github.project.openubl.quarkus.xbuilder.runtime;

import io.quarkus.qute.Locate;
import io.quarkus.qute.TemplateLocator;
import io.quarkus.qute.Variant;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Optional;

@Locate(CustomTemplateLocator.PREFIX + "*")
public class CustomTemplateLocator implements TemplateLocator {

    public static final String PREFIX = "xbuilder:";

    @Override
    public int getPriority() {
        return DEFAULT_PRIORITY - 1;
    }

    @Override
    public Optional<TemplateLocation> locate(String path) {
        path = path.replace(PREFIX, "templates/Renderer/");

        InputStream is = locatePath(path);

        if (is == null) {
            return Optional.empty();
        } else {
            return Optional.of(buildTemplateLocation(is));
        }
    }

    private InputStream locatePath(String path) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl == null) {
            cl = CustomTemplateLocator.class.getClassLoader();
        }
        return cl.getResourceAsStream(path);
    }

    private TemplateLocation buildTemplateLocation(InputStream is) {
        return new TemplateLocation() {
            @Override
            public Reader read() {
                return new InputStreamReader(is);
            }

            @Override
            public Optional<Variant> getVariant() {
                return Optional.empty();
            }
        };
    }
}
