package ar.com.ironsoft.marroccl.web.guice.init;

import com.google.inject.TypeLiteral;
import com.google.inject.matcher.AbstractMatcher;

/**
 * @author Tomas de Priede
 */
public class InitializationMatcher extends AbstractMatcher<TypeLiteral<?>> {

    @Override
    public boolean matches(TypeLiteral<?> typeLiteral) {
        return HasInitialization.class.isAssignableFrom(typeLiteral
                .getRawType());
    }
}
