package ar.com.ironsoft.marrocclandroid.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by gabrielvilloldo on 4/10/15.
 */
public class PushMessage implements Parcelable {
    private String gameId;
    private String title;
    private String message;
    private String videoLink;
    private String thumbnailLink;
    private String gifLink;
    private String additionalData;
    private Integer minutes;
    private Integer seconds;
    private String type;
    private String player;
    private String player2;

    public static final Parcelable.Creator<PushMessage> CREATOR = new Parcelable.Creator<PushMessage>() {
        @Override
        public PushMessage createFromParcel(Parcel source) {
            return new PushMessage(source);
        }

        @Override
        public PushMessage[] newArray(int size) {
            return new PushMessage[size];
        }
    };

    public PushMessage() { }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
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

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getThumbnailLink() {
        return thumbnailLink;
    }

    public void setThumbnailLink(String thumbnailLink) {
        this.thumbnailLink = thumbnailLink;
    }

    public String getGifLink() {
        return gifLink;
    }

    public void setGifLink(String gifLink) {
        this.gifLink = gifLink;
    }

    public String getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(String aditionalData) {
        this.additionalData = aditionalData;
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

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player) {
        this.player2 = player;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(gameId);
        dest.writeString(title);
        dest.writeString(message);
        dest.writeString(videoLink);
        dest.writeString(thumbnailLink);
        dest.writeString(gifLink);
        dest.writeString(additionalData);
        dest.writeInt(minutes);
        dest.writeInt(seconds);
        dest.writeString(type);
        dest.writeString(player);
    }

    private PushMessage(Parcel source) {
        gameId = source.readString();
        title = source.readString();
        message = source.readString();
        videoLink = source.readString();
        thumbnailLink = source.readString();
        gifLink = source.readString();
        additionalData = source.readString();
        minutes = source.readInt();
        seconds = source.readInt();
        type = source.readString();
        player = source.readString();
    }
}
