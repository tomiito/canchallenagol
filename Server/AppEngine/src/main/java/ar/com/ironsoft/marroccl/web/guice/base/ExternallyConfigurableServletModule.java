package ar.com.ironsoft.marroccl.web.guice.base;

import javax.servlet.http.HttpServlet;

/**
 * @author Tomas de Priede
 */
public interface ExternallyConfigurableServletModule {

    public void serve(String path, Class<? extends HttpServlet> servlet);
}
