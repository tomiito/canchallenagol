package ar.com.ironsoft.marroccl.web.app.modules.config.guice;

import java.util.LinkedList;

import javax.servlet.http.HttpServlet;

import ar.com.ironsoft.marroccl.web.app.modules.config.endpoints.ConfigEndpointApi;
import ar.com.ironsoft.marroccl.web.app.modules.config.model.ConfigHolder;
import ar.com.ironsoft.marroccl.web.guice.GuiceServletsModule;

public class ConfigModule extends GuiceServletsModule {

    @Override
    public LinkedList<Class<?>> getOfyClasses() {
        LinkedList<Class<?>> registeredClasses = new LinkedList<>();
        //
        registeredClasses.add(ConfigHolder.class);
        //
        return registeredClasses;
    }

    @Override
    public LinkedList<Class<? extends HttpServlet>> getServletClasses() {
        LinkedList<Class<? extends HttpServlet>> registeredClasses = new LinkedList<>();
        //
        //
        return registeredClasses;
    }

    @Override
    public LinkedList<Class<?>> getEndpointClasses() {
        LinkedList<Class<?>> registeredClasses = new LinkedList<>();
        //
        registeredClasses.add(ConfigEndpointApi.class);
        //
        return registeredClasses;
    }
}
