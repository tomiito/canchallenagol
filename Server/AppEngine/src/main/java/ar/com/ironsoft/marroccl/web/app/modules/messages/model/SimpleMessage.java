package ar.com.ironsoft.marroccl.web.app.modules.messages.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import ar.com.ironsoft.marroccl.web.gcm.GCMMessage;

/**
 * @author Tomas de Priede
 */
public class SimpleMessage implements Serializable {

    private String cursor;
    private String message;

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GCMMessage asGCM() throws UnsupportedEncodingException {
        return new GCMMessage.Builder().addData("message", message).build();
    }
}
