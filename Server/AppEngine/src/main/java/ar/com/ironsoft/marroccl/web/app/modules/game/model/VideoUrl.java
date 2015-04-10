package ar.com.ironsoft.marroccl.web.app.modules.game.model;

import java.util.UUID;

import ar.com.ironsoft.marroccl.web.core.model.BaseModel;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * @author Tomas de Priede
 */
@Entity
@Index
public class VideoUrl implements BaseModel {

    @Id
    private String id;
    private String videoUrl;
    private Integer videoMinute;
    private String gameId;

    public VideoUrl() {
    }

    public VideoUrl(String gameId, String videoUrl, Integer videoMinute) {
        this.gameId = gameId;
        this.videoUrl = videoUrl;
        this.videoMinute = videoMinute;
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Integer getVideoMinute() {
        return videoMinute;
    }

    public void setVideoMinute(Integer videoMinute) {
        this.videoMinute = videoMinute;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
