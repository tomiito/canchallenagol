package ar.com.ironsoft.marroccl.web.app.modules.messages.guice;

import java.util.LinkedList;

import javax.servlet.http.HttpServlet;

import ar.com.ironsoft.marroccl.web.app.modules.messages.endpoints.MessagesEndpointApi;
import ar.com.ironsoft.marroccl.web.app.modules.messages.model.Multicast;
import ar.com.ironsoft.marroccl.web.app.modules.messages.servlets.SendAllMessageServlet;
import ar.com.ironsoft.marroccl.web.app.modules.messages.tasks.SendAllMessageTask;
import ar.com.ironsoft.marroccl.web.app.modules.messages.tasks.SendMessageTask;
import ar.com.ironsoft.marroccl.web.guice.GuiceServletsModule;

/**
 * @author Tomas de Priede
 */
public class MessagesModule extends GuiceServletsModule {

    @Override
    public LinkedList<Class<?>> getOfyClasses() {
        LinkedList<Class<?>> registeredClasses = new LinkedList<>();
        //
        registeredClasses.add(Multicast.class);
        //
        return registeredClasses;
    }

    @Override
    public LinkedList<Class<? extends HttpServlet>> getServletClasses() {
        LinkedList<Class<? extends HttpServlet>> registeredClasses = new LinkedList<>();
        // Servlets
        registeredClasses.add(SendAllMessageServlet.class);
        // Tasks
        registeredClasses.add(SendAllMessageTask.class);
        registeredClasses.add(SendMessageTask.class);
        //
        return registeredClasses;
    }

    @Override
    public LinkedList<Class<?>> getEndpointClasses() {
        LinkedList<Class<?>> registeredClasses = new LinkedList<>();
        //
        registeredClasses.add(MessagesEndpointApi.class);
        //
        return registeredClasses;
    }
}
