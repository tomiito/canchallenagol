package ar.com.ironsoft.marroccl.web.app.modules.game.services;

import java.io.IOException;
import java.net.URL;

import ar.com.ironsoft.marroccl.web.app.modules.config.daos.ConfigHolderDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.daos.GameDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.daos.GameGoalDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.model.Game;
import ar.com.ironsoft.marroccl.web.app.modules.game.model.GameGoal;
import ar.com.ironsoft.marroccl.web.app.modules.game.xml.GameXmlService;
import ar.com.ironsoft.marroccl.web.app.modules.game.xml.model.Commentary;
import ar.com.ironsoft.marroccl.web.core.constants.SharedConstants;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Tomas de Priede
 */
@Singleton
public class GameService {

    private GameDao gameDao;
    private GameGoalDao gameGoalDao;
    private GameXmlService gameXmlService;
    private CommentaryService commentaryService;
    private ConfigHolderDao configHolderDao;
    private ScoreService scoreService;

    public Game getGameWithCurrentScore(String gameId, Integer minute,
            Integer second) {
        Game game = gameDao.get(Game.class, gameId);
        return getGameWithCurrentScore(game, minute, second);
    }

    public Game getGameWithCurrentScore(Game game, Integer minute,
            Integer second) {
        Preconditions.checkNotNull(game);
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
        return game;
    }

    public void parseGame() throws IOException {
        // Receive game
        URL url = new URL(configHolderDao.getConfig().getXmlUrl());
        HTTPRequest gaeRequest = new HTTPRequest(url, HTTPMethod.GET);
        gaeRequest.addHeader(new HTTPHeader("accept", "application/xml"));
        HTTPResponse xmlResponse = URLFetchServiceFactory.getURLFetchService()
                .fetch(gaeRequest);
        //
        String xml = new String(xmlResponse.getContent(), SharedConstants.UTF_8);
        Commentary commentary = gameXmlService.parseGameXml(xml);
        commentaryService.saveCommentary(commentary);
        scoreService.processGoals(commentary);
    }

    public void startGame(String inProgressGameId, int minute, int second) {
        Game game = gameDao.get(Game.class, inProgressGameId);
        game.start(minute, second);
        gameDao.save(game);
    }

    public void stopGame(String inProgressGameId) {
        Game game = gameDao.get(Game.class, inProgressGameId);
        game.stop();
        gameDao.save(game);
    }

    @Inject
    public void setScoreService(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @Inject
    public void setConfigHolderDao(ConfigHolderDao configHolderDao) {
        this.configHolderDao = configHolderDao;
    }

    @Inject
    public void setCommentaryService(CommentaryService commentaryService) {
        this.commentaryService = commentaryService;
    }

    @Inject
    public void setGameXmlService(GameXmlService gameXmlService) {
        this.gameXmlService = gameXmlService;
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
