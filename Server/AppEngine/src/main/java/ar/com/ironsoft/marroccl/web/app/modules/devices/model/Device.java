package ar.com.ironsoft.marroccl.web.app.modules.devices.model;

import java.util.Date;

import ar.com.ironsoft.marroccl.web.core.model.BaseModel;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * @author Tomas de Priede
 */
@Entity
@Index
public class Device implements BaseModel {

    @Id
    private String deviceId;
    private Date registeredOn;

    public Device() {
    }

    public Device(String deviceId) {
        this.deviceId = deviceId;
        this.registeredOn = new Date();
    }

    @Override
    public String getId() {
        return deviceId;
    }

    @Override
    public void setId(String id) {
        setDeviceId(id);
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Date getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(Date registeredOn) {
        this.registeredOn = registeredOn;
    }
}
