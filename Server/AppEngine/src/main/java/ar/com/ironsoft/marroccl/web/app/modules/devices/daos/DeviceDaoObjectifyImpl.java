package ar.com.ironsoft.marroccl.web.app.modules.devices.daos;

import ar.com.ironsoft.marroccl.web.app.modules.devices.model.Device;
import ar.com.ironsoft.marroccl.web.core.daos.BaseDaoObjectifyImpl;
import ar.com.ironsoft.marroccl.web.core.daos.OfyService;
import ar.com.ironsoft.marroccl.web.core.model.paging.PagingResult;

import com.google.inject.Singleton;

/**
 * @author Tomas de Priede
 */
@Singleton
public class DeviceDaoObjectifyImpl extends BaseDaoObjectifyImpl<Device>
        implements DeviceDao {

    public PagingResult<Device> getPageOfAll(Integer pageSize, String cursor) {
        return getPagingResult(OfyService.ofy().load().type(Device.class),
                pageSize, cursor);
    }
}
