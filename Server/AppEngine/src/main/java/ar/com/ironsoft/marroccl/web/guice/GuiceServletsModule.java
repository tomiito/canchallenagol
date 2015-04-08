package ar.com.ironsoft.marroccl.web.guice;

import java.util.LinkedList;

import javax.servlet.http.HttpServlet;

import ar.com.ironsoft.marroccl.web.guice.base.ExternallyConfigurableServletModule;
import ar.com.ironsoft.marroccl.web.guice.base.URLPaths;
import ar.com.ironsoft.marroccl.web.guice.init.InitializationMatcher;
import ar.com.ironsoft.marroccl.web.guice.init.InitializationTypeListener;

import com.google.api.server.spi.guice.GuiceSystemServiceServletModule;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.inject.Provides;

/**
 * @author Tomas de Priede
 */
public abstract class GuiceServletsModule extends
        GuiceSystemServiceServletModule implements
        ExternallyConfigurableServletModule {

    private final LinkedList<Class<? extends HttpServlet>> servletClasses = new LinkedList<>();
    private final LinkedList<Class<?>> endpointClasses = new LinkedList<>();
    private final LinkedList<Class<?>> allClasses = new LinkedList<>();
    //
    private GuiceClassesHolder guiceClassesHolder;

    //
    public LinkedList<Class<? extends HttpServlet>> getServletClasses() {
        LinkedList<Class<? extends HttpServlet>> list = new LinkedList<>();
        return list;
    }

    public LinkedList<Class<?>> getEndpointClasses() {
        LinkedList<Class<?>> list = new LinkedList<>();
        return list;
    }

    protected void addModules(GuiceServletsModule... modules) {
        Preconditions.checkNotNull(modules);
        //
        for (GuiceServletsModule module : modules) {
            addModule(module);
        }
    }

    protected void addModule(GuiceServletsModule module) {
        servletClasses.addAll(module.getServletClasses());
        endpointClasses.addAll(module.getEndpointClasses());
    }

    @Provides
    @com.google.inject.Singleton
    public GuiceClassesHolder provideGuiceClassesHolder() {
        return guiceClassesHolder;
    }

    @Override
    public void serve(String path, Class<? extends HttpServlet> servlet) {
        super.serve(path).with(servlet);
    }

    @Override
    protected void configureServlets() {
        this.addModule(this);
        //
        Iterables.addAll(allClasses, servletClasses);
        //
        for (Class clazz : allClasses) {
            URLPaths.base(clazz).apply(this);
        }
        //
        this.serveGuiceSystemServiceServlet("/_ah/spi/*", endpointClasses);
        //
        guiceClassesHolder = new GuiceClassesHolder(allClasses);
        //
        bindListener(new InitializationMatcher(),
                new InitializationTypeListener());
    }
}
