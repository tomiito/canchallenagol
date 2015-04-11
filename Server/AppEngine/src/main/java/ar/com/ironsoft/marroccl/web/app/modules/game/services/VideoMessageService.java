package ar.com.ironsoft.marroccl.web.app.modules.game.services;

import java.util.logging.Level;
import java.util.logging.Logger;

import ar.com.ironsoft.marroccl.web.app.modules.config.model.ConfigHolder;
import ar.com.ironsoft.marroccl.web.app.modules.game.daos.MessageDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.daos.VideoUrlDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.model.TitleMessage;
import ar.com.ironsoft.marroccl.web.app.modules.game.model.VideoUrl;
import ar.com.ironsoft.marroccl.web.app.modules.game.xml.model.Message;
import ar.com.ironsoft.marroccl.web.app.modules.messages.model.VideoMessage;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Tomas de Priede
 */
@Singleton
public class VideoMessageService {

    private Logger logger = Logger.getLogger(VideoMessageService.class
            .getSimpleName());
    private MessageDao messageDao;
    private VideoUrlDao videoUrlDao;
    private CommentaryService commentaryService;

    public VideoMessage createVideoMessage(ConfigHolder configHolder,
            String messageId) {
        Message message = messageDao.get(Message.class, messageId);
        return createVideoMessage(configHolder, message);
    }

    public VideoMessage createVideoMessage(ConfigHolder configHolder,
            Message message) {
        int videoMinute = message.getMinute() + configHolder.getExtraMinutes();
        if (message.getPeriod() == 2) {
            videoMinute += configHolder.getExtraMinutes2Period();
        }
        if (message.getSecond() > 30) {
            videoMinute++;
        }
        VideoUrl videoUrl = videoUrlDao.findByMinute(videoMinute);
        //
        TitleMessage titleMessage = commentaryService
                .parseTitleMessage(message);
        //
        VideoMessage videoMessage = new VideoMessage();
        videoMessage.setGameId(String.valueOf(configHolder
                .getInProgressGameId()));
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
        return videoMessage;
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
    public void setCommentaryService(CommentaryService commentaryService) {
        this.commentaryService = commentaryService;
    }
}
