package ar.com.ironsoft.marroccl.web.app.modules.messages.model;

import java.util.Set;
import java.util.UUID;

import ar.com.ironsoft.marroccl.web.core.model.BaseModel;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * @author Tomas de Priede
 */
@Entity
@Index
public class Multicast implements BaseModel {

    @Id
    private String multicastId;
    private Set<String> deviceIds;

    public Multicast() {
    }

    public Multicast(String multicastId) {
        this.multicastId = multicastId;
    }

    public void generateId() {
        multicastId = UUID.randomUUID().toString();
    }

    public Set<String> getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(Set<String> deviceIds) {
        this.deviceIds = deviceIds;
    }

    public String getMulticastId() {
        return multicastId;
    }

    public void setMulticastId(String multicastId) {
        this.multicastId = multicastId;
    }

    @Override
    public String getId() {
        return multicastId;
    }

    @Override
    public void setId(String id) {
        setMulticastId(multicastId);
    }
}
