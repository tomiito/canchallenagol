package ar.com.ironsoft.marroccl.web.app.modules.messages.daos;

import ar.com.ironsoft.marroccl.web.app.modules.messages.model.Multicast;
import ar.com.ironsoft.marroccl.web.core.daos.BaseDao;

import com.google.inject.ImplementedBy;

/**
 * @author Tomas de Priede
 */
@ImplementedBy(MulticastDaoObjectifyImpl.class)
public interface MulticastDao extends BaseDao<Multicast> {
}
