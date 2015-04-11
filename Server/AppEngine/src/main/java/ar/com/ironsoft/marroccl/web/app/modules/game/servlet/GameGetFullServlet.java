package ar.com.ironsoft.marroccl.web.app.modules.game.servlet;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.com.ironsoft.marroccl.web.app.modules.config.daos.ConfigHolderDao;
import ar.com.ironsoft.marroccl.web.app.modules.config.model.ConfigHolder;
import ar.com.ironsoft.marroccl.web.app.modules.game.daos.CommentaryDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.daos.GameDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.daos.MessageDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.model.Game;
import ar.com.ironsoft.marroccl.web.app.modules.game.services.GameService;
import ar.com.ironsoft.marroccl.web.app.modules.game.services.VideoMessageService;
import ar.com.ironsoft.marroccl.web.app.modules.game.xml.model.Message;
import ar.com.ironsoft.marroccl.web.app.modules.messages.model.VideoMessage;
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

    private GameDao gameDao;
    private GameService gameService;
    private CommentaryDao commentaryDao;
    private ConfigHolderDao configHolderDao;
    private VideoMessageService videoMessageService;
    private MessageDao messageDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String gameId = req.getParameter("gameId");
        Preconditions.checkNotNull(gameId);
        //
        Game game = gameDao.get(Game.class, gameId);
        game.refreshCurrentTime();
        int minute = game.getCurrentMinute();
        int second = game.getCurrentSecond();
        game = gameService.getGameWithCurrentScore(game, minute, second);
        gameDao.save(game);
        //
        ConfigHolder configHolder = configHolderDao.getConfig();
        //
        List<Message> messages = messageDao.getAll(minute, second);
        Collections.sort(messages);
        for (Message message : messages) {
            if (message.isNotFinal() || 3 == game.getStatus()) {
                VideoMessage videoMessage = videoMessageService
                        .createVideoMessage(configHolder, message);
                game.getMessages().add(videoMessage);
            }
        }
        //
        String json = new Gson().toJson(game);
        setJsonResponse(resp, json);
    }

    @Inject
    public void setGameDao(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    @Inject
    public void setMessageDao(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @Inject
    public void setConfigHolderDao(ConfigHolderDao configHolderDao) {
        this.configHolderDao = configHolderDao;
    }

    @Inject
    public void setCommentaryDao(CommentaryDao commentaryDao) {
        this.commentaryDao = commentaryDao;
    }

    @Inject
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    @Inject
    public void setVideoMessageService(VideoMessageService videoMessageService) {
        this.videoMessageService = videoMessageService;
    }
}
