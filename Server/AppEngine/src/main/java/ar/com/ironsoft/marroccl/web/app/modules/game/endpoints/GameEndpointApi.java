package ar.com.ironsoft.marroccl.web.app.modules.game.endpoints;

import java.io.IOException;
import java.util.Collection;

import ar.com.ironsoft.marroccl.web.app.modules.game.daos.CommentaryDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.daos.MessageDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.model.TitleMessage;
import ar.com.ironsoft.marroccl.web.app.modules.game.services.CommentaryService;
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

    private CommentaryDao commentaryDao;
    private MessageDao messageDao;
    private TaskLauncher taskLauncher;
    private CommentaryService commentaryService;

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
    public void pushMessage(@Named("gameId") String gameId,
            @Named("messageId") String messageId) throws IOException {
        Message message = messageDao.get(Message.class, messageId);
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
        // TODO remove hardcode video
        videoMessage
                .setVideoLink("https://s3.amazonaws.com/historico.lanacion.com.ar/Partidos/TYC.20150331_211215.mp4");
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
}
