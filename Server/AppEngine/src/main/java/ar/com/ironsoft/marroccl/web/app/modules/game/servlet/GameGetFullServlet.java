package ar.com.ironsoft.marroccl.web.app.modules.game.servlet;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.com.ironsoft.marroccl.web.app.modules.config.daos.ConfigHolderDao;
import ar.com.ironsoft.marroccl.web.app.modules.config.model.ConfigHolder;
import ar.com.ironsoft.marroccl.web.app.modules.game.daos.CommentaryDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.daos.MessageDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.model.Game;
import ar.com.ironsoft.marroccl.web.app.modules.game.services.GameService;
import ar.com.ironsoft.marroccl.web.app.modules.game.services.VideoMessageService;
import ar.com.ironsoft.marroccl.web.app.modules.game.xml.model.Commentary;
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
        int minute = 67;
        int second = 56;
        Game game = gameService.getGameWithCurrentScore(gameId, minute, second);
        //
        ConfigHolder configHolder = configHolderDao.getConfig();
        Commentary commentary = commentaryDao.get(Commentary.class,
                Commentary.COMMENTARY_ID);
        //
        Collection<Message> messages = messageDao.get(Message.class,
                commentary.getMessageListId()).values();
        for (Message message : messages) {
            if (message.getMinute() <= minute && message.getSecond() <= second
                    && message.isNotFinal()) {
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
