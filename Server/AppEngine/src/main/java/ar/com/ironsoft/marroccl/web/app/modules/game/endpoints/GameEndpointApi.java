package ar.com.ironsoft.marroccl.web.app.modules.game.endpoints;

import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import ar.com.ironsoft.marroccl.web.app.modules.config.daos.ConfigHolderDao;
import ar.com.ironsoft.marroccl.web.app.modules.config.model.ConfigHolder;
import ar.com.ironsoft.marroccl.web.app.modules.game.daos.CommentaryDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.daos.MessageDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.daos.VideoUrlDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.model.TitleMessage;
import ar.com.ironsoft.marroccl.web.app.modules.game.model.VideoUrl;
import ar.com.ironsoft.marroccl.web.app.modules.game.services.CommentaryService;
import ar.com.ironsoft.marroccl.web.app.modules.game.services.GameDummyService;
import ar.com.ironsoft.marroccl.web.app.modules.game.services.GameService;
import ar.com.ironsoft.marroccl.web.app.modules.game.tasks.FindUrlsTaskServlet;
import ar.com.ironsoft.marroccl.web.app.modules.game.xml.model.Commentary;
import ar.com.ironsoft.marroccl.web.app.modules.game.xml.model.Message;
import ar.com.ironsoft.marroccl.web.app.modules.messages.model.VideoMessage;
import ar.com.ironsoft.marroccl.web.app.modules.messages.tasks.SendAllMessageTask;
import ar.com.ironsoft.marroccl.web.core.tasks.TaskLauncher;
import ar.com.ironsoft.marroccl.web.core.tasks.TaskParameter;
import ar.com.ironsoft.marroccl.web.core.utils.ObjectSerializationUtils;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * @author Tomas de Priede
 */
@Api(name = "gameApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "ironsoft", ownerName = "MarrocCL", packagePath = "app/game"))
public class GameEndpointApi {

    private Logger logger = Logger.getLogger(GameEndpointApi.class
            .getSimpleName());
    private CommentaryDao commentaryDao;
    private MessageDao messageDao;
    private TaskLauncher taskLauncher;
    private CommentaryService commentaryService;
    private ConfigHolderDao configHolderDao;
    private VideoUrlDao videoUrlDao;
    private GameDummyService gameDummyService;
    private GameService gameService;

    @ApiMethod
    public void findUrls(@Named("gameId") String gameId) {
        //
        taskLauncher.launchTask(TaskLauncher.QUEUE_FIND_URL_PAGED,
                FindUrlsTaskServlet.class, new TaskParameter(
                        Commentary.GAME_ID, gameId));
    }

    @ApiMethod
    public Commentary getGameCommentary() {
        Commentary commentary = commentaryDao.get(Commentary.class,
                Commentary.COMMENTARY_ID);
        //
        Collection<Message> messages = messageDao.get(Message.class,
                commentary.getMessageListId()).values();
        commentary.setMessageList(Lists.newArrayList(messages));
        //
        return commentary;
    }

    @ApiMethod(httpMethod = "post")
    public void parseGame() throws IOException {
        gameService.parseGame();
    }

    @ApiMethod(httpMethod = "post")
    public void createDummyGames() {
        gameDummyService.createDummyGames();
    }

    @ApiMethod(httpMethod = "post")
    public void pushMessage(@Named("gameId") String gameId,
            @Named("messageId") String messageId) throws IOException {
        ConfigHolder configHolder = configHolderDao.getConfig();
        Message message = messageDao.get(Message.class, messageId);
        int videoMinute = message.getMinute() + configHolder.getExtraMinutes();
        if (message.getSecond() > 30) {
            videoMinute++;
        }
        VideoUrl videoUrl = videoUrlDao.findByMinute(videoMinute);
        //
        TitleMessage titleMessage = commentaryService
                .parseTitleMessage(message);
        //
        VideoMessage videoMessage = new VideoMessage();
        videoMessage.setGameId(gameId);
        videoMessage.setTitle(titleMessage.getTitle());
        videoMessage.setMessage(titleMessage.getMessage());
        videoMessage.setType(message.getType());
        //
        String[] players = commentaryService.parsePlayer(message);
        videoMessage.setPlayer(players[0]);
        videoMessage.setPlayer2(players[1]);
        //
        if (videoUrl != null) {
            videoMessage.setVideoLink(videoUrl.getVideoUrl());
        } else {
            logger.log(Level.WARNING, "Video not found in minute: "
                    + videoMinute);
            videoMessage.setVideoLink(null);
        }
        videoMessage.setGifLink("");
        videoMessage.setThumbnailLink("");
        //
        videoMessage.setPeriod(message.getPeriod());
        videoMessage.setMinutes(message.getMinute());
        videoMessage.setSeconds(message.getSecond());
        //
        taskLauncher.launchTask(TaskLauncher.QUEUE_GCM_PAGED,
                SendAllMessageTask.class,
                ObjectSerializationUtils.serialize(videoMessage));
    }

    @Inject
    public void setCommentaryService(CommentaryService commentaryService) {
        this.commentaryService = commentaryService;
    }

    @Inject
    public void setTaskLauncher(TaskLauncher taskLauncher) {
        this.taskLauncher = taskLauncher;
    }

    @Inject
    public void setCommentaryDao(CommentaryDao commentaryDao) {
        this.commentaryDao = commentaryDao;
    }

    @Inject
    public void setMessageDao(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @Inject
    public void setVideoUrlDao(VideoUrlDao videoUrlDao) {
        this.videoUrlDao = videoUrlDao;
    }

    @Inject
    public void setConfigHolderDao(ConfigHolderDao configHolderDao) {
        this.configHolderDao = configHolderDao;
    }

    @Inject
    public void setGameDummyService(GameDummyService gameDummyService) {
        this.gameDummyService = gameDummyService;
    }

    @Inject
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }
}
