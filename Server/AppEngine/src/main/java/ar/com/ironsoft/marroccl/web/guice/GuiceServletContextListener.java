package ar.com.ironsoft.marroccl.web.guice;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.google.inject.Guice;
import com.google.inject.Module;

public class GuiceServletContextListener implements ServletContextListener {

    public static final String MODULES_ATTRIBUTE = "guice-modules";
    public static final String INJECTOR_ATTRIBUTE = "guice-injector";

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        context.removeAttribute(INJECTOR_ATTRIBUTE);
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        List<Module> modules = new ArrayList<Module>();
        String moduleNames = context.getInitParameter(MODULES_ATTRIBUTE);
        if (moduleNames != null) {
            for (String moduleName : moduleNames.split(",")) {
                try {
                    modules.add((Module) Class.forName(moduleName.trim())
                            .newInstance());
                } catch (Exception e) {
                    throw new RuntimeException("Fail to load guice modules", e);
                }
            }
        }
        context.setAttribute(INJECTOR_ATTRIBUTE, Guice.createInjector(modules));
    }
}
