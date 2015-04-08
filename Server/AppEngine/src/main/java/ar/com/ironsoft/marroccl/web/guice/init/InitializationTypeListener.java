package ar.com.ironsoft.marroccl.web.guice.init;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

/**
 * @author Tomas de Priede
 */
public class InitializationTypeListener implements TypeListener {

    @Override
    public <I> void hear(final TypeLiteral<I> typeLiteral,
            TypeEncounter<I> typeEncounter) {
        TypeEncounter<HasInitialization> encounter = (TypeEncounter<HasInitialization>) typeEncounter;
        encounter.register(new InitializationInjectionListener());
    }
}
