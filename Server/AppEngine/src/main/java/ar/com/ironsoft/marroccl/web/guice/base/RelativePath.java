package ar.com.ironsoft.marroccl.web.guice.base;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation configures a relative path for a URLPaths.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RelativePath {

    String value();
}
