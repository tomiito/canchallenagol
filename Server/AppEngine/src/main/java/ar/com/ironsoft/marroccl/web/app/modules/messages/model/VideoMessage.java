package ar.com.ironsoft.marroccl.web.app.modules.messages.model;

import ar.com.ironsoft.marroccl.web.gcm.GCMMessage;

/**
 * @author Tomas de Priede
 */
public class VideoMessage extends SimpleMessage {

    private String videoLink;
    private String gifLink;
    private String thumbnailLink;

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

    public GCMMessage asGCM() {
        return new GCMMessage.Builder().addData("message", getMessage()) //
                .addData("videoLink", getVideoLink()) //
                .addData("gifLink", getGifLink()) //
                .addData("thumbnailLink", getThumbnailLink()).build();
    }
}
