package ar.com.ironsoft.marrocclandroid.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gabrielvilloldo on 4/10/15.
 */
public class PushMessage implements Parcelable {
    public String title;
    public String message;
    public String videoLink;
    public String thumbnailLink;
    public String gifLink;
    public String additionalData;
    public Integer timeMinutes;
    public Integer timeSeconds;
    public String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(message);
        dest.writeString(videoLink);
        dest.writeString(thumbnailLink);
        dest.writeString(gifLink);
        dest.writeString(additionalData);
        dest.writeInt(timeMinutes);
        dest.writeInt(timeSeconds);
        dest.writeString(type);
    }

    private PushMessage(Parcel source) {
        title = source.readString();
        message = source.readString();
        videoLink = source.readString();
        thumbnailLink = source.readString();
        gifLink = source.readString();
        additionalData = source.readString();
        timeMinutes = source.readInt();
        timeSeconds = source.readInt();
        type = source.readString();

    }
}
