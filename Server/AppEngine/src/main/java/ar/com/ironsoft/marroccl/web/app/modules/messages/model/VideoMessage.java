package ar.com.ironsoft.marroccl.web.app.modules.messages.model;

import ar.com.ironsoft.marroccl.web.gcm.GCMMessage;

/**
 * @author Tomas de Priede
 */
public class VideoMessage extends SimpleMessage {

    private String videoLink;
    private String gifLink;
    private String thumbnailLink;
    private String type;
    private Integer period;
    private Integer minutes;
    private Integer seconds;
    private String title;

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getGifLink() {
        return gifLink;
    }

    public void setGifLink(String gifLink) {
        this.gifLink = gifLink;
    }

    public String getThumbnailLink() {
        return thumbnailLink;
    }

    public void setThumbnailLink(String thumbnailLink) {
        this.thumbnailLink = thumbnailLink;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GCMMessage asGCM() {
        return new GCMMessage.Builder().addData("message", getMessage()) //
                .addData("videoLink", getVideoLink()) //
                .addData("gifLink", getGifLink()) //
                .addData("type", getType()) //
                .addData("period", String.valueOf(getPeriod())) //
                .addData("minutes", String.valueOf(getMinutes())) //
                .addData("seconds", String.valueOf(getSeconds())) //
                .addData("thumbnailLink", getThumbnailLink()).build();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
