package ar.com.ironsoft.marroccl.web.app.modules.game.xml.model;

import java.util.Date;

import org.dom4j.Element;

import ar.com.ironsoft.marroccl.web.core.model.BaseModel;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

/**
 *
 * @author Tomas de Priede
 */
@Entity
@Index
public class Message extends BaseElement implements BaseModel {

    @Id
    private String messageId;
    @Unindex
    private String comment;
    @Unindex
    private Date lastModified;
    @Unindex
    private Integer period;
    @Unindex
    private Integer minute;
    @Unindex
    private Integer second;
    @Unindex
    private String time;
    @Unindex
    private String type;

    public Message() {
    }

    public Message(Element element) {
        parseAttributes(element);
    }

    public void parseAttributes(Element element) {
        messageId = element.attributeValue("id");
        comment = element.attributeValue("comment");
        lastModified = parseDate(element.attributeValue("last_modified"));
        period = parseInteger(element.attributeValue("period"));
        minute = parseInteger(element.attributeValue("minute"));
        second = parseInteger(element.attributeValue("second"));
        type = element.attributeValue("type");
        time = element.attributeValue("time");
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getId() {
        return messageId;
    }

    @Override
    public void setId(String id) {
        setMessageId(id);
    }
}
