package ar.com.ironsoft.marroccl.web.app.guice;

import java.util.LinkedList;

import javax.servlet.http.HttpServlet;

import ar.com.ironsoft.marroccl.web.app.servlets.IndexServlet;
import ar.com.ironsoft.marroccl.web.guice.GuiceServletsModule;

/**
 * @author Tomas de Priede
 */
public class AppModule extends GuiceServletsModule {

    @Override
    public LinkedList<Class<? extends HttpServlet>> getServletClasses() {
        LinkedList<Class<? extends HttpServlet>> servletClasses = new LinkedList<>();
        servletClasses.add(IndexServlet.class);
        return servletClasses;
    }
}
