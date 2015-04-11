package ar.com.ironsoft.marroccl.web.app.modules.game.model;

import java.util.ArrayList;
import java.util.List;

import ar.com.ironsoft.marroccl.web.app.modules.messages.model.VideoMessage;
import ar.com.ironsoft.marroccl.web.core.model.BaseModel;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.IgnoreSave;
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
    /**
     * 1 - not started <br />
     * 2 - in progress <br />
     * 3 - finished <br />
     */
    @Unindex
    private Integer status;
    @Index
    private String homeTeamId;
    @Unindex
    private String homeTeamName;
    @Unindex
    private String homeTeamLink;
    @Unindex
    private Integer homeTeamScore;
    @Index
    private String awayTeamId;
    @Unindex
    private String awayTeamName;
    @Unindex
    private String awayTeamLink;
    @Unindex
    private Integer awayTeamScore;
    /**
     * Time in string MM:ss
     */
    @Unindex
    private String time;
    //
    @Unindex
    private int currentMinute;
    @Unindex
    private int currentSecond;
    @Unindex
    private long lastMillis;
    @IgnoreSave
    private List<VideoMessage> messages = new ArrayList<>();

    //
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

    public String getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(String homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getHomeTeamLink() {
        return homeTeamLink;
    }

    public void setHomeTeamLink(String homeTeamLink) {
        this.homeTeamLink = homeTeamLink;
    }

    public String getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(String awayTeamId) {
        this.awayTeamId = awayTeamId;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public String getAwayTeamLink() {
        return awayTeamLink;
    }

    public void setAwayTeamLink(String awayTeamLink) {
        this.awayTeamLink = awayTeamLink;
    }

    public Integer getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(Integer homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public Integer getAwayTeamScore() {
        return awayTeamScore;
    }

    public void setAwayTeamScore(Integer awayTeamScore) {
        this.awayTeamScore = awayTeamScore;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<VideoMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<VideoMessage> messages) {
        this.messages = messages;
    }

    public int getCurrentMinute() {
        return currentMinute;
    }

    public void setCurrentMinute(int currentMinute) {
        this.currentMinute = currentMinute;
    }

    public int getCurrentSecond() {
        return currentSecond;
    }

    public void setCurrentSecond(int currentSecond) {
        this.currentSecond = currentSecond;
    }

    public long getLastMillis() {
        return lastMillis;
    }

    public void setLastMillis(long lastMillis) {
        this.lastMillis = lastMillis;
    }

    public void stop() {
        lastMillis = 0l;
        currentMinute = 0;
        currentSecond = 0;
        status = 1;
    }

    public void start(int minute, int second) {
        currentMinute = minute;
        currentSecond = second;
        lastMillis = System.currentTimeMillis();
        status = 2;
    }

    public void refreshCurrentTime() {
        // In progress status 2
        if (status == 2) {
            long millis = System.currentTimeMillis();
            long diff = (millis - lastMillis) / 1000;
            currentMinute += diff / 60;
            currentSecond += diff % 60;
            if (currentSecond >= 60) {
                currentMinute += currentSecond / 60;
                currentSecond = currentSecond % 60;
            }
            lastMillis = millis;
            if (currentMinute > 95){
                status = 3;
            }
        }
    }
}
