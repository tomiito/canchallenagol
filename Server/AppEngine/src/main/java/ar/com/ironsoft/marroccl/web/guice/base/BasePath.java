package ar.com.ironsoft.marroccl.web.guice.base;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Tomas de Priede
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface BasePath {

    String value();
}
