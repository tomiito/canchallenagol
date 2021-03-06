package ar.com.ironsoft.marroccl.web.app.modules.game.guice;

import java.util.LinkedList;

import javax.servlet.http.HttpServlet;

import ar.com.ironsoft.marroccl.web.app.modules.game.endpoints.GameEndpointApi;
import ar.com.ironsoft.marroccl.web.app.modules.game.model.Game;
import ar.com.ironsoft.marroccl.web.app.modules.game.model.GameGoal;
import ar.com.ironsoft.marroccl.web.app.modules.game.model.VideoUrl;
import ar.com.ironsoft.marroccl.web.app.modules.game.servlet.GameGetFullServlet;
import ar.com.ironsoft.marroccl.web.app.modules.game.servlet.GameGetServlet;
import ar.com.ironsoft.marroccl.web.app.modules.game.servlet.GameListServlet;
import ar.com.ironsoft.marroccl.web.app.modules.game.servlet.ParseGameServlet;
import ar.com.ironsoft.marroccl.web.app.modules.game.tasks.FindUrlTaskServlet;
import ar.com.ironsoft.marroccl.web.app.modules.game.tasks.FindUrlsTaskServlet;
import ar.com.ironsoft.marroccl.web.app.modules.game.xml.model.Commentary;
import ar.com.ironsoft.marroccl.web.app.modules.game.xml.model.Message;
import ar.com.ironsoft.marroccl.web.guice.GuiceServletsModule;

public class GameModule extends GuiceServletsModule {

    @Override
    public LinkedList<Class<?>> getOfyClasses() {
        LinkedList<Class<?>> registeredClasses = new LinkedList<>();
        //
        registeredClasses.add(Game.class);
        registeredClasses.add(GameGoal.class);
        registeredClasses.add(Commentary.class);
        registeredClasses.add(Message.class);
        registeredClasses.add(VideoUrl.class);
        //
        return registeredClasses;
    }

    @Override
    public LinkedList<Class<? extends HttpServlet>> getServletClasses() {
        LinkedList<Class<? extends HttpServlet>> registeredClasses = new LinkedList<>();
        //
        registeredClasses.add(GameGetServlet.class);
        registeredClasses.add(GameGetFullServlet.class);
        registeredClasses.add(GameListServlet.class);
        registeredClasses.add(ParseGameServlet.class);
        registeredClasses.add(FindUrlsTaskServlet.class);
        registeredClasses.add(FindUrlTaskServlet.class);
        //
        return registeredClasses;
    }

    @Override
    public LinkedList<Class<?>> getEndpointClasses() {
        LinkedList<Class<?>> registeredClasses = new LinkedList<>();
        //
        registeredClasses.add(GameEndpointApi.class);
        //
        return registeredClasses;
    }
}
