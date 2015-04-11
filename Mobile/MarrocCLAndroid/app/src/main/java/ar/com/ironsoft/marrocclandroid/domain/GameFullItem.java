package ar.com.ironsoft.marrocclandroid.domain;

import java.util.ArrayList;

/**
 * Created by gabrielvilloldo on 4/11/15.
 */
public class GameFullItem extends GameItem {
    private ArrayList<PushMessage> messages;

    public ArrayList<PushMessage> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<PushMessage> messages) {
        this.messages = messages;
    }
}
