package ar.com.ironsoft.marroccl.web.app.modules.config.endpoints;

import java.io.IOException;

import ar.com.ironsoft.marroccl.web.app.modules.config.daos.ConfigHolderDao;
import ar.com.ironsoft.marroccl.web.app.modules.config.model.ConfigHolder;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.inject.Inject;

/**
 * @author Tomas de Priede
 */
@Api(name = "configApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "ironsoft", ownerName = "MarrocCL", packagePath = "app/config"))
public class ConfigEndpointApi {

    private ConfigHolderDao configHolderDao;

    @ApiMethod
    public ConfigHolder getConfigHolder() {
        return configHolderDao.getConfig();
    }

    @ApiMethod(httpMethod = "post")
    public void setConfig(@Named("xmlUrl") String xmlUrl,
            @Named("videoUrl") String videoUrl,
            @Named("startHour") Integer startHour,
            @Named("startMinute") Integer startMinute,
            @Named("totalVideos") Integer totalVideos) throws IOException {
        ConfigHolder configHolder = configHolderDao.getConfig();
        //
        configHolderDao.save(configHolder);
    }

    @Inject
    public void setConfigHolderDao(ConfigHolderDao configHolderDao) {
        this.configHolderDao = configHolderDao;
    }
}
