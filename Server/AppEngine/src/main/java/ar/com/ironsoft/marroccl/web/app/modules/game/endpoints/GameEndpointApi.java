package ar.com.ironsoft.marroccl.web.app.modules.game.endpoints;

import java.util.Collection;

import ar.com.ironsoft.marroccl.web.app.modules.game.daos.CommentaryDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.daos.MessageDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.xml.model.Commentary;
import ar.com.ironsoft.marroccl.web.app.modules.game.xml.model.Message;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * @author Tomas de Priede
 */
@Api(name = "gameApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "ironsoft", ownerName = "MarrocCL", packagePath = "app/game"))
public class GameEndpointApi {

    private CommentaryDao commentaryDao;
    private MessageDao messageDao;

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

    @Inject
    public void setCommentaryDao(CommentaryDao commentaryDao) {
        this.commentaryDao = commentaryDao;
    }

    @Inject
    public void setMessageDao(MessageDao messageDao) {
        this.messageDao = messageDao;
    }
}
