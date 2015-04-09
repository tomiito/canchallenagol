package ar.com.ironsoft.marroccl.web.app.modules.messages.services;

import java.util.List;
import java.util.Set;

import ar.com.ironsoft.marroccl.web.app.modules.devices.model.Device;
import ar.com.ironsoft.marroccl.web.app.modules.messages.daos.MulticastDao;
import ar.com.ironsoft.marroccl.web.app.modules.messages.model.Multicast;

import com.google.appengine.labs.repackaged.com.google.common.base.Function;
import com.google.appengine.labs.repackaged.com.google.common.collect.Lists;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
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
        Set<String> deviceIds = Sets.newHashSet(Lists.transform(deviceList,
                new Function<Device, String>() {

                    @Override
                    public String apply(Device device) {
                        return device.getDeviceId();
                    }
                }));
        multicast.generateId();
        multicast.setDeviceIds(deviceIds);
        multicastDao.save(multicast);
        return multicast.getId();
    }

    @Inject
    public void setMulticastDao(MulticastDao multicastDao) {
        this.multicastDao = multicastDao;
    }
}
