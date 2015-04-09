package ar.com.ironsoft.marroccl.web.app.modules.messages.model;

import java.io.Serializable;

/**
 * @author Tomas de Priede
 */
public class MulticastMessage implements Serializable {

    private String multicastKey;
    private SimpleMessage message;

    public MulticastMessage() {
    }

    public MulticastMessage(String multicastKey, SimpleMessage message) {
        this.multicastKey = multicastKey;
        this.message = message;
    }

    public String getMulticastKey() {
        return multicastKey;
    }

    public void setMulticastKey(String multicastKey) {
        this.multicastKey = multicastKey;
    }

    public SimpleMessage getMessage() {
        return message;
    }

    public void setMessage(SimpleMessage message) {
        this.message = message;
    }
}
