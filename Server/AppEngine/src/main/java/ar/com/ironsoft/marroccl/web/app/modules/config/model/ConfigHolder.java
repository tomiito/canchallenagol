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
    private Integer totalVideos = 100;

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

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}