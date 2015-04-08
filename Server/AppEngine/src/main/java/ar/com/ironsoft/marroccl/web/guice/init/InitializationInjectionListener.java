package ar.com.ironsoft.marroccl.web.guice.init;

import com.google.inject.spi.InjectionListener;

/**
 * @author Tomas de Priede
 */
public class InitializationInjectionListener implements
        InjectionListener<HasInitialization> {

    @Override
    public void afterInjection(HasInitialization injectee) {
        injectee.initialize();
    }
}
