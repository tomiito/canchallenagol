package ar.com.ironsoft.marroccl.web.app.modules.config.daos;

import ar.com.ironsoft.marroccl.web.app.modules.config.model.ConfigHolder;
import ar.com.ironsoft.marroccl.web.core.daos.BaseDao;

import com.google.inject.ImplementedBy;

/**
 * @author Tomas de Priede
 */
@ImplementedBy(ConfigHolderDaoObjectifyImpl.class)
public interface ConfigHolderDao extends BaseDao<ConfigHolder> {

    ConfigHolder getConfig();
}
