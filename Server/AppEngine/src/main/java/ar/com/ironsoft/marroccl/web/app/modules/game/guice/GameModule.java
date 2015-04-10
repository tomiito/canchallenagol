package ar.com.ironsoft.marroccl.web.app.modules.game.guice;

import java.util.LinkedList;

import javax.servlet.http.HttpServlet;

import ar.com.ironsoft.marroccl.web.guice.GuiceServletsModule;

public class GameModule extends GuiceServletsModule {

    @Override
    public LinkedList<Class<?>> getOfyClasses() {
        LinkedList<Class<?>> registeredClasses = new LinkedList<>();
        //
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
        //
        return registeredClasses;
    }
}
