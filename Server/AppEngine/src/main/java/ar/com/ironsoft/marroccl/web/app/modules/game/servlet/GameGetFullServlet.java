package ar.com.ironsoft.marroccl.web.app.modules.game.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.com.ironsoft.marroccl.web.app.modules.game.model.Game;
import ar.com.ironsoft.marroccl.web.app.modules.game.services.GameService;
import ar.com.ironsoft.marroccl.web.core.servlets.BaseServlet;
import ar.com.ironsoft.marroccl.web.guice.base.BasePath;
import ar.com.ironsoft.marroccl.web.guice.base.RelativePath;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Get game by minute and second. Do not return the historic of the events in
 * the match before that time. Shows the score in that time.
 * 
 * @author Tomas de Priede
 */
@Singleton
@BasePath("/game/")
@RelativePath("getFull")
public class GameGetFullServlet extends BaseServlet {

    private GameService gameService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String gameId = req.getParameter("gameId");
        Integer minute = Integer.parseInt(req.getParameter("minute"));
        Integer second = Integer.parseInt(req.getParameter("second"));
        Preconditions.checkNotNull(gameId);
        //
        Game game = gameService.getGameWithCurrentScore(gameId, minute, second);

        //
        String json = new Gson().toJson(game);
        setJsonResponse(resp, json);
    }

    @Inject
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }
}
