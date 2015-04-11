package ar.com.ironsoft.marroccl.web.app.modules.game.xml.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Tomas de Priede
 */
public class MessageTest {

    @Test
    public void test_compare() {
        // diff period
        Message message1 = newMessage(1, 1, 2);
        Message message2 = newMessage(2, 3, 2);
        assertEquals(1, message1.compareTo(message2));
        assertEquals(-1, message2.compareTo(message1));
        // diff minute
        message1 = newMessage(2, 1, 2);
        message2 = newMessage(2, 3, 2);
        assertEquals(1, message1.compareTo(message2));
        assertEquals(-1, message2.compareTo(message1));
        // diff second
        message1 = newMessage(2, 3, 2);
        message2 = newMessage(2, 3, 4);
        assertEquals(1, message1.compareTo(message2));
        assertEquals(-1, message2.compareTo(message1));
    }

    private Message newMessage(int period, int minute, int second) {
        Message message = new Message();
        message.setPeriod(period);
        message.setMinute(minute);
        message.setSecond(second);
        return message;
    }
}
