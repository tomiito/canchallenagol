package ar.com.ironsoft.marroccl.web.app.modules.game.model;

import java.util.Date;

import ar.com.ironsoft.marroccl.web.core.model.BaseModel;

/**
 * @author Tomas de Priede
 */
public interface Commentary extends BaseModel {

    public String getGameId();

    public void setGameId(String gameId);

    public Integer getHomeScore();

    public void setHomeScore(Integer homeScore);

    public Integer getHomeTeamId();

    public void setHomeTeamId(Integer homeTeamId);

    public String getHomeTeamName();

    public void setHomeTeamName(String homeTeamName);

    public Integer getAwayScore();

    public void setAwayScore(Integer awayScore);

    public Integer getAwayTeamId();

    public void setAwayTeamId(Integer awayTeamId);

    public String getAwayTeamName();

    public void setAwayTeamName(String awayTeamName);

    public String getCompetition();

    public void setCompetition(String competition);

    public Integer getCompetitionId();

    public void setCompetitionId(Integer competitionId);

    public String getLangId();

    public void setLangId(String langId);

    public String getMatchday();

    public void setMatchday(String matchday);

    public Integer getSeasonId();

    public void setSeasonId(Integer seasonId);

    public String getSeason();

    public void setSeason(String season);

    public Integer getSportId();

    public void setSportId(Integer sportId);

    public String getSportName();

    public void setSportName(String sportName);

    public Date getDate();

    public void setDate(Date date);
}
