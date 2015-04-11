package ar.com.ironsoft.marroccl.web.app.modules.game.model;

import ar.com.ironsoft.marroccl.web.core.model.BaseModel;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

/**
 * @author Tomas de Priede
 */
@Entity
@Index
public class Game implements BaseModel {

    @Id
    private String gameId;
    @Index
    private String homeTeamId;
    @Unindex
    private String homeTeamName;
    @Unindex
    private String homeTeamLink;
    @Index
    private String awayTeamId;
    @Unindex
    private String awayTeamName;
    @Unindex
    private String awayTeamLink;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    @Override
    public String getId() {
        return gameId;
    }

    @Override
    public void setId(String id) {
        setGameId(id);
    }
}
