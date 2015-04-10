package ar.com.ironsoft.marroccl.web.app.modules.game.services;

import java.util.logging.Level;
import java.util.logging.Logger;

import ar.com.ironsoft.marroccl.web.app.modules.game.daos.CommentaryDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.daos.MessageDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.xml.model.Commentary;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Tomas de Priede
 */
@Singleton
public class CommentaryService {

    private Logger logger = Logger.getLogger(CommentaryService.class
            .getSimpleName());
    private CommentaryDao commentaryDao;
    private MessageDao messageDao;

    public void saveCommentary(Commentary commentary) {
        logger.log(Level.INFO, "Removing old commentary");
        // Look for existent game id;
        commentaryDao.delete(Commentary.class, commentary.getId());
        messageDao.deleteEntities(commentary.getMessageList());
        //
        logger.log(Level.INFO,
                "Saving new commentary with id: " + commentary.getGameId());
        //
        commentaryDao.save(commentary);
        messageDao.saveAll(commentary.getMessageList());
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
