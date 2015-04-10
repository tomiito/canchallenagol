package ar.com.ironsoft.marroccl.web.app.modules.game.xml.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

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
public class Commentary extends BaseElement implements BaseModel {

    public static final String COMMENTARY_ID = "COMMENTARY_ID";
    @Id
    private String id = COMMENTARY_ID;
    @Unindex
    private String gameId;
    @Unindex
    private Integer homeScore;
    @Unindex
    private Integer homeTeamId;
    @Unindex
    private String homeTeamName;
    @Unindex
    private Integer awayScore;
    @Unindex
    private Integer awayTeamId;
    @Unindex
    private String awayTeamName;
    @Unindex
    private String competition;
    @Unindex
    private Integer competitionId;
    @Unindex
    private String langId;
    @Unindex
    private String matchday;
    @Unindex
    private Integer seasonId;
    @Unindex
    private String season;
    @Unindex
    private Integer sportId;
    @Unindex
    private String sportName;
    @Unindex
    private Date date;
    @Unindex
    private List<String> messageListId = new ArrayList<>();
    @IgnoreSave
    private List<Message> messageList = new ArrayList<>();

    public Commentary() {
    }

    public Commentary(Element element) {
        parseAttributes(element);
        parseChilds(element);
    }

    private void parseAttributes(Element element) {
        homeScore = parseInteger(element.attributeValue("home_score"));
        homeTeamId = parseInteger(element.attributeValue("home_team_id"));
        homeTeamName = element.attributeValue("home_team_name");
        //
        awayScore = parseInteger(element.attributeValue("away_score"));
        awayTeamId = parseInteger(element.attributeValue("away_team_id"));
        awayTeamName = element.attributeValue("away_team_name");
        //
        competition = element.attributeValue("competition");
        competitionId = parseInteger(element.attributeValue("competition_id"));
        gameId = element.attributeValue("game_id");
        langId = element.attributeValue("lang_id");
        matchday = element.attributeValue("matchday");
        season = element.attributeValue("season");
        seasonId = parseInteger(element.attributeValue("season_id"));
        sportId = parseInteger(element.attributeValue("sport_id"));
        sportName = element.attributeValue("sport_name");
        date = parseDate(element.attributeValue("game_date"));
    }

    private void parseChilds(Element element) {
        for (Iterator i = element.elementIterator(); i.hasNext();) {
            Message message = new Message((Element) i.next());
            // do something
            messageListId.add(message.getMessageId());
            messageList.add(message);
        }
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Integer getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(Integer homeScore) {
        this.homeScore = homeScore;
    }

    public Integer getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(Integer homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public Integer getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(Integer awayScore) {
        this.awayScore = awayScore;
    }

    public Integer getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(Integer awayTeamId) {
        this.awayTeamId = awayTeamId;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public String getCompetition() {
        return competition;
    }

    public void setCompetition(String competition) {
        this.competition = competition;
    }

    public Integer getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Integer competitionId) {
        this.competitionId = competitionId;
    }

    public String getLangId() {
        return langId;
    }

    public void setLangId(String langId) {
        this.langId = langId;
    }

    public String getMatchday() {
        return matchday;
    }

    public void setMatchday(String matchday) {
        this.matchday = matchday;
    }

    public Integer getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(Integer seasonId) {
        this.seasonId = seasonId;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public Integer getSportId() {
        return sportId;
    }

    public void setSportId(Integer sportId) {
        this.sportId = sportId;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public List<String> getMessageListId() {
        return messageListId;
    }

    public void setMessageListId(List<String> messageListId) {
        this.messageListId = messageListId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
