package ar.com.ironsoft.marroccl.web.app.modules.messages.services;

import java.util.List;

import ar.com.ironsoft.marroccl.web.app.modules.devices.model.Device;
import ar.com.ironsoft.marroccl.web.app.modules.messages.daos.MulticastDao;
import ar.com.ironsoft.marroccl.web.app.modules.messages.model.Multicast;

import com.google.appengine.labs.repackaged.com.google.common.base.Function;
import com.google.appengine.labs.repackaged.com.google.common.collect.Lists;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Tomas de Priede
 */
@Singleton
public class MulticastService {

    private MulticastDao multicastDao;

    public String createMulticast(List<Device> deviceList) {
        Preconditions.checkNotNull(deviceList);
        //
        Multicast multicast = new Multicast();
        List<String> deviceIds = Lists.transform(deviceList,
                new Function<Device, String>() {

                    @Override
                    public String apply(Device device) {
                        return device.getDeviceId();
                    }
                });
        multicast.generateId();
        multicast.setDeviceIds(deviceIds);
        multicastDao.save(multicast);
        return multicast.getId();
    }

    public void updateMulticast(String multicastId, List<String> deviceList) {
        Preconditions.checkNotNull(multicastId);
        Preconditions.checkNotNull(deviceList);
        //
        Multicast multicast = multicastDao.get(Multicast.class, multicastId);
        multicast.setDeviceIds(deviceList);
        multicastDao.save(multicast);
    }

    @Inject
    public void setMulticastDao(MulticastDao multicastDao) {
        this.multicastDao = multicastDao;
    }
}
