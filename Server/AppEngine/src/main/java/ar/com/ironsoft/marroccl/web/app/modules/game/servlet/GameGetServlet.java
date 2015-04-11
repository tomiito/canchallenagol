package ar.com.ironsoft.marroccl.web.app.modules.game.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.com.ironsoft.marroccl.web.app.modules.game.daos.GameDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.daos.GameGoalDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.model.Game;
import ar.com.ironsoft.marroccl.web.app.modules.game.model.GameGoal;
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
@RelativePath("get")
public class GameGetServlet extends BaseServlet {

    private GameDao gameDao;
    private GameGoalDao gameGoalDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String gameId = req.getParameter("gameId");
        Integer minute = Integer.parseInt(req.getParameter("minute"));
        Integer second = Integer.parseInt(req.getParameter("second"));
        Preconditions.checkNotNull(gameId);
        //
        Game game = gameDao.get(Game.class, gameId);
        // Replace final score by current score in that minute
        GameGoal currentScore = gameGoalDao.findByMinuteAndSecond(minute,
                second);
        if (currentScore != null) {
            game.setHomeTeamScore(currentScore.getHomeTeamScore());
            game.setAwayTeamScore(currentScore.getAwayTeamScore());
        } else {
            game.setHomeTeamScore(0);
            game.setAwayTeamScore(0);
        }
        //
        String json = new Gson().toJson(game);
        setJsonResponse(resp, json);
    }

    @Inject
    public void setGameGoalDao(GameGoalDao gameGoalDao) {
        this.gameGoalDao = gameGoalDao;
    }

    @Inject
    public void setGameDao(GameDao gameDao) {
        this.gameDao = gameDao;
    }
}
