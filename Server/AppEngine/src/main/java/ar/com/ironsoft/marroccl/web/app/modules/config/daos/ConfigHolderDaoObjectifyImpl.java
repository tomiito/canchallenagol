package ar.com.ironsoft.marroccl.web.app.modules.config.daos;

import ar.com.ironsoft.marroccl.web.app.modules.config.model.ConfigHolder;
import ar.com.ironsoft.marroccl.web.core.daos.BaseDaoObjectifyImpl;
import ar.com.ironsoft.marroccl.web.core.exceptions.EntityNotFoundException;

import com.google.inject.Singleton;

/**
 * @author Tomas de Priede
 */
@Singleton
public class ConfigHolderDaoObjectifyImpl extends
        BaseDaoObjectifyImpl<ConfigHolder> implements ConfigHolderDao {

    @Override
    public ConfigHolder getConfig() {
        ConfigHolder configHolder = null;
        try {
            configHolder = get(ConfigHolder.class, ConfigHolder.CONFIG_ID);
        } catch (EntityNotFoundException e) {
            configHolder = new ConfigHolder();
            save(configHolder);
        }
        return configHolder;
    }
}
