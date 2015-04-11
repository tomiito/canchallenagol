package ar.com.ironsoft.marroccl.web.app.modules.config.model;

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
public class ConfigHolder implements BaseModel {

    public static final String CONFIG_ID = "CONFIG";
    @Id
    private String id = CONFIG_ID;
    @Unindex
    private String xmlUrl = "http://cdntx.lanacion.com.ar/hackaton/incidencias.xml";
    @Unindex
    private String videoUrl = "https://s3.amazonaws.com/historico.lanacion.com.ar/Partidos/TYC.20150331_{0}.mp4";
    @Unindex
    private Integer startHour = 21;
    @Unindex
    private Integer startMinute = 1;
    @Unindex
    private Integer totalVideos = 140;
    /**
     * This is the minutes that I have to add to the startup time because the
     * video starts from minute 0, but game could start in minute 5 or later.
     */
    @Unindex
    private Integer extraMinutes = 3;
    @Unindex
    private Integer extraMinutes2Period = 20;
    @Unindex
    private String inProgressGameId = "1";
    /**
     * 1 mean auto push the goal to devices
     */
    @Unindex
    private Integer autoPushGoals = 0;

    public String getXmlUrl() {
        return xmlUrl;
    }

    public void setXmlUrl(String xmlUrl) {
        this.xmlUrl = xmlUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public Integer getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(Integer startMinute) {
        this.startMinute = startMinute;
    }

    public Integer getTotalVideos() {
        return totalVideos;
    }

    public void setTotalVideos(Integer totalVideos) {
        this.totalVideos = totalVideos;
    }

    public Integer getExtraMinutes() {
        if (extraMinutes == null) {
            extraMinutes = 0;
        }
        return extraMinutes;
    }

    public void setExtraMinutes(Integer extraMinutes) {
        this.extraMinutes = extraMinutes;
    }

    public Integer getExtraMinutes2Period() {
        return extraMinutes2Period;
    }

    public void setExtraMinutes2Period(Integer extraMinutes2Period) {
        this.extraMinutes2Period = extraMinutes2Period;
    }

    public Integer getAutoPushGoals() {
        if (autoPushGoals == null) {
            autoPushGoals = 0;
        }
        return autoPushGoals;
    }

    public void setAutoPushGoals(Integer autoPushGoals) {
        this.autoPushGoals = autoPushGoals;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getInProgressGameId() {
        if (inProgressGameId == null) {
            inProgressGameId = "1";
        }
        return inProgressGameId;
    }

    public void setInProgressGameId(String inProgressGameId) {
        this.inProgressGameId = inProgressGameId;
    }
}
