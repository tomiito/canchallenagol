package ar.com.ironsoft.marroccl.web.app.modules.devices.guice;

import java.util.LinkedList;

import ar.com.ironsoft.marroccl.web.app.modules.devices.endpoints.DevicesEndpointApi;
import ar.com.ironsoft.marroccl.web.app.modules.devices.model.Device;
import ar.com.ironsoft.marroccl.web.guice.GuiceServletsModule;

public class DeviceModule extends GuiceServletsModule {

    @Override
    public LinkedList<Class<?>> getOfyClasses() {
        LinkedList<Class<?>> registeredClasses = new LinkedList<>();
        //
        registeredClasses.add(Device.class);
        //
        return registeredClasses;
    }

    @Override
    public LinkedList<Class<?>> getEndpointClasses() {
        LinkedList<Class<?>> registeredClasses = new LinkedList<>();
        //
        registeredClasses.add(DevicesEndpointApi.class);
        //
        return registeredClasses;
    }
}
