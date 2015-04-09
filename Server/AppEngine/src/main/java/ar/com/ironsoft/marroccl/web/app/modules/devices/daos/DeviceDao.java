package ar.com.ironsoft.marroccl.web.app.modules.devices.daos;

import ar.com.ironsoft.marroccl.web.app.modules.devices.model.Device;
import ar.com.ironsoft.marroccl.web.core.daos.BaseDao;
import ar.com.ironsoft.marroccl.web.core.model.paging.PagingResult;

import com.google.inject.ImplementedBy;

/**
 * @author Tomas de Priede
 */
@ImplementedBy(DeviceDaoObjectifyImpl.class)
public interface DeviceDao extends BaseDao<Device> {

    public PagingResult<Device> getPageOfAll(Integer pageSize, String cursor);
}
