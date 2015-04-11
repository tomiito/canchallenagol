package ar.com.ironsoft.marrocclandroid.domain;

/**
 * Created by gabrielvilloldo on 4/11/15.
 */
public class GameItem {
    private String gameId;
    private Integer status;
    private String homeTeamName;
    private String homeTeamLink;
    private Integer homeTeamScore;
    private String awayTeamName;
    private String awayTeamLink;
    private Integer awayTeamScore;
    private String time;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(Integer homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
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
}
