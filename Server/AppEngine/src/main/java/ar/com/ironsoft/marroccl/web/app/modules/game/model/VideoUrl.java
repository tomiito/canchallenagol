package ar.com.ironsoft.marroccl.web.app.modules.game.model;

import java.util.UUID;

import ar.com.ironsoft.marroccl.web.core.model.BaseModel;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

/**
 * @author Tomas de Priede
 */
@Entity
@Index
public class VideoUrl implements BaseModel {

    private String id;
    private String videoUrl;
    private String gameId;

    public VideoUrl() {
    }

    public VideoUrl(String gameId, String videoUrl) {
        this.gameId = gameId;
        this.videoUrl = videoUrl;
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

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
