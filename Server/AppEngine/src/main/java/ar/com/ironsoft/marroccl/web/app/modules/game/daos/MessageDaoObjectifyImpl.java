package ar.com.ironsoft.marroccl.web.app.modules.game.daos;

import ar.com.ironsoft.marroccl.web.app.modules.game.xml.model.MessageElement;
import ar.com.ironsoft.marroccl.web.core.daos.BaseDaoObjectifyImpl;

import com.google.inject.Singleton;

/**
 * @author Tomas de Priede
 */
@Singleton
public class MessageDaoObjectifyImpl extends
        BaseDaoObjectifyImpl<MessageElement> implements MessageDao {
}