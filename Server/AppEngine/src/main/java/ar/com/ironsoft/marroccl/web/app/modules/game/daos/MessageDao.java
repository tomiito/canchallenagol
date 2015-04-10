package ar.com.ironsoft.marroccl.web.app.modules.game.daos;

import ar.com.ironsoft.marroccl.web.app.modules.game.xml.model.Message;
import ar.com.ironsoft.marroccl.web.core.daos.BaseDao;

import com.google.inject.ImplementedBy;

/**
 * @author Tomas de Priede
 */
@ImplementedBy(MessageDaoObjectifyImpl.class)
public interface MessageDao extends BaseDao<Message> {
}
