package ar.com.ironsoft.marroccl.web.app.modules.game.model;

/**
 * Useful to split the title and message from the xml
 * 
 * @author Tomas de Priede
 */
public class TitleMessage {

    private String title;
    private String message;

    public TitleMessage() {
    }

    public TitleMessage(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
