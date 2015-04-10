package ar.com.ironsoft.marrocclandroid.domain;

/**
 * Created by gabrielvilloldo on 4/10/15.
 */
public class PushMessage {
    public String title;
    public String message;
    public String url;
    public String thumbnail;
    public String gifUrl;
    public String additionalData;
    public Integer timeMinutes;
    public Integer timeSeconds;
    public Integer type;

    public PushMessage(String title, String message) {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public void setGifUrl(String gifUrl) {
        this.gifUrl = gifUrl;
    }

    public String getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(String aditionalData) {
        this.additionalData = aditionalData;
    }

    public Integer getTimeMinutes() {
        return timeMinutes;
    }

    public void setTimeMinutes(Integer timeMinutes) {
        this.timeMinutes = timeMinutes;
    }

    public Integer getTimeSeconds() {
        return timeSeconds;
    }

    public void setTimeSeconds(Integer timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
