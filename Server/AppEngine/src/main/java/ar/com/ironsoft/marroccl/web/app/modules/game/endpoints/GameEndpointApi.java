package ar.com.ironsoft.marroccl.web.app.modules.game.endpoints;

import java.io.IOException;
import java.util.Collection;
import java.util.logging.Logger;

import ar.com.ironsoft.marroccl.web.app.modules.config.daos.ConfigHolderDao;
import ar.com.ironsoft.marroccl.web.app.modules.config.model.ConfigHolder;
import ar.com.ironsoft.marroccl.web.app.modules.game.daos.CommentaryDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.daos.GameDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.daos.MessageDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.model.Game;
import ar.com.ironsoft.marroccl.web.app.modules.game.services.GameDummyService;
import ar.com.ironsoft.marroccl.web.app.modules.game.services.GameService;
import ar.com.ironsoft.marroccl.web.app.modules.game.services.VideoMessageService;
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
    private ConfigHolderDao configHolderDao;
    private GameDummyService gameDummyService;
    private GameService gameService;
    private GameDao gameDao;
    private VideoMessageService videoMessageService;

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
    public void startGame(@Named("minute") int minute,
            @Named("second") int second) throws IOException {
        ConfigHolder configHolder = configHolderDao.getConfig();
        gameService.startGame(configHolder.getInProgressGameId(), minute,
                second);
    }

    @ApiMethod(httpMethod = "post")
    public void stopGame() throws IOException {
        ConfigHolder configHolder = configHolderDao.getConfig();
        gameService.stopGame(configHolder.getInProgressGameId());
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
        VideoMessage videoMessage = videoMessageService.createVideoMessage(
                configHolder, messageId);
        Game game = gameDao.get(Game.class, configHolder.getInProgressGameId());
        game.start(videoMessage.getMinutes(), videoMessage.getSeconds());
        gameDao.save(game);
        //
        taskLauncher.launchTask(TaskLauncher.QUEUE_GCM_PAGED,
                SendAllMessageTask.class,
                ObjectSerializationUtils.serialize(videoMessage));
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

    @Inject
    public void setGameDao(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    @Inject
    public void setVideoMessageService(VideoMessageService videoMessageService) {
        this.videoMessageService = videoMessageService;
    }
}
