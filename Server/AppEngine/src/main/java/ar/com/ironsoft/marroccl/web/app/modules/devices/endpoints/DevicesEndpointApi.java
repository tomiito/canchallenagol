package ar.com.ironsoft.marroccl.web.app.modules.devices.endpoints;

import java.util.Set;

import ar.com.ironsoft.marroccl.web.app.modules.devices.daos.DeviceDao;
import ar.com.ironsoft.marroccl.web.app.modules.devices.model.Device;
import ar.com.ironsoft.marroccl.web.app.modules.devices.model.DeviceTableResponse;
import ar.com.ironsoft.marroccl.web.core.model.paging.Cursor;
import ar.com.ironsoft.marroccl.web.core.model.paging.PagingResult;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.Nullable;
import com.google.inject.Inject;

/**
 * @author Tomas de Priede
 */
@Api(name = "devicesApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "ironsoft", ownerName = "MarrocCL", packagePath = "app/devices"))
public class DevicesEndpointApi {

    private DeviceDao deviceDao;

    @ApiMethod
    public DeviceTableResponse getDevices(
            @Nullable @Named("cursors") Set<Cursor> cursors,
            @Named("size") Integer size, @Named("offset") Integer offset) {
        String cursor = Cursor.findNextAction(cursors, offset);
        PagingResult<Device> page = deviceDao.getPageOfAll(size, cursor);
        return new DeviceTableResponse(size, offset, page, cursors, cursor);
    }

    @ApiMethod
    public Device getDevice(@Named("deviceId") String deviceId) {
        return deviceDao.get(Device.class, deviceId);
    }

    @ApiMethod(httpMethod = "post")
    public Device createDevice(@Named("deviceId") String deviceId) {
        Device device = new Device(deviceId);
        deviceDao.save(device);
        return device;
    }

    @ApiMethod(httpMethod = "delete")
    public void deleteDevice(@Named("deviceId") String deviceId) {
        deviceDao.delete(Device.class, deviceId);
    }

    @Inject
    public void setDeviceDao(DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }
}
