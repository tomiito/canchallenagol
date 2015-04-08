package ar.com.ironsoft.marroccl.web.guice.base;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;

import com.google.common.base.Preconditions;

/**
 *
 */
public class URLPaths<T extends HttpServlet> {

    private final Logger logger = Logger.getLogger(URLPaths.class
            .getSimpleName());
    private final HashMap<String, Class<T>> paths = new HashMap<String, Class<T>>();

    private URLPaths(String basePath, Class<T>[] servletClasses) {
        for (int i = 0; (servletClasses != null) && (i < servletClasses.length); i++) {
            Preconditions
                    .checkNotNull(
                            servletClasses[i].getAnnotation(RelativePath.class),
                            new StringBuilder(servletClasses[i]
                                    .getCanonicalName())
                                    .append(" servlet for ")
                                    .append(basePath)
                                    .append(" basePath does not have a @RelativePath annotation.")
                                    .toString());
            paths.put(normalizeSlashes(basePath
                    + "/"
                    + servletClasses[i].getAnnotation(RelativePath.class)
                            .value()), servletClasses[i]);
        }
    }

    private URLPaths(Class<T>... servletClasses) {
        for (int i = 0; (servletClasses != null) && (i < servletClasses.length); i++) {
            this.paths.put(findTerminatedPath(servletClasses[i]),
                    servletClasses[i]);
        }
    }

    public static URLPaths base(String basePath, Class... servlets) {
        checkNotNull(basePath, "No path specified.");
        basePath = basePath.trim();
        checkArgument(basePath.startsWith("/"),
                "basePath must start from the root of the context");
        return new URLPaths(basePath, servlets);
    }

    public static URLPaths base(Class... servlets) {
        return new URLPaths(servlets);
    }

    public void apply(ExternallyConfigurableServletModule module) {
        for (Map.Entry<String, Class<T>> entry : this.paths.entrySet()) {
            logger.info(String.format("Registering url: %s with class: %s",
                    entry.getKey(), entry.getValue()));
            module.serve(entry.getKey(), entry.getValue());
        }
    }

    public static <T extends Class<? extends Object>> String findTerminatedPath(
            T clazz) {
        Preconditions.checkNotNull(clazz);
        //
        String className = clazz.getCanonicalName();
        String basePathValue = null;
        String relativePathValue = null;
        BasePath basePath = clazz.getAnnotation(BasePath.class);
        if (basePath != null) {
            basePathValue = basePath.value();
        } else {
            // check for Cron
            Cron cronPath = clazz.getAnnotation(Cron.class);
            if (cronPath != null) {
                basePathValue = "/cron";
            } else {
                Task taskPath = clazz.getAnnotation(Task.class);
                if (taskPath != null) {
                    basePathValue = "/tasks";
                }
            }
        }
        //
        RelativePath relativePath = clazz.getAnnotation(RelativePath.class);
        if (relativePath != null) {
            relativePathValue = relativePath.value();
        }
        //
        checkNotNull(basePathValue, new StringBuilder("No base path on ")
                .append(className).toString());
        checkNotNull(relativePathValue, new StringBuilder(
                "Not relative path on ").append(className).toString());
        //
        StringBuilder path = new StringBuilder(basePathValue).append("/")
                .append(relativePathValue);
        String terminatedPath = normalizeSlashes(path.toString());
        return terminatedPath;
    }

    protected static String normalizeSlashes(String input) {
        Preconditions.checkNotNull(input);
        return input.replaceAll("//", "/");
    }
}
