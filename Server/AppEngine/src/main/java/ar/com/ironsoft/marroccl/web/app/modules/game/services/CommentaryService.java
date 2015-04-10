package ar.com.ironsoft.marroccl.web.app.modules.game.services;

import ar.com.ironsoft.marroccl.web.app.modules.game.daos.CommentaryDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.daos.MessageDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.xml.model.CommentaryElement;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Tomas de Priede
 */
@Singleton
public class CommentaryService {

    private CommentaryDao commentaryDao;
    private MessageDao messageDao;

    public void saveCommentary(CommentaryElement commentary) {
        // Look for existent game id;
        commentaryDao.delete(CommentaryElement.class, commentary.getId());
        messageDao.deleteEntities(commentary.getMessageList());
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
