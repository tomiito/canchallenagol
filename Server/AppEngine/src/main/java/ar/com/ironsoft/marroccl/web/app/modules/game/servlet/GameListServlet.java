package ar.com.ironsoft.marroccl.web.app.modules.game.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.com.ironsoft.marroccl.web.app.modules.game.daos.GameDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.model.Game;
import ar.com.ironsoft.marroccl.web.app.modules.game.services.GameService;
import ar.com.ironsoft.marroccl.web.core.model.paging.PagingResult;
import ar.com.ironsoft.marroccl.web.core.servlets.BaseServlet;
import ar.com.ironsoft.marroccl.web.guice.base.BasePath;
import ar.com.ironsoft.marroccl.web.guice.base.RelativePath;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Tomas de Priede
 */
@Singleton
@BasePath("/game/")
@RelativePath("list")
public class GameListServlet extends BaseServlet {

    private GameDao gameDao;
    private GameService gameService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        PagingResult<Game> pagingResult = gameDao.getPageOfAll(Game.class, 100,
                null);
        //
        for (Game game : pagingResult.getResultList()) {
            game.refreshCurrentTime();
            int minute = game.getCurrentMinute();
            int second = game.getCurrentSecond();
            game = gameService.getGameWithCurrentScore(game, minute, second);
            gameDao.save(game);
        }
        //
        String json = new Gson().toJson(pagingResult.getResultList());
        setJsonResponse(resp, json);
    }

    @Inject
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    @Inject
    public void setGameDao(GameDao gameDao) {
        this.gameDao = gameDao;
    }
}
