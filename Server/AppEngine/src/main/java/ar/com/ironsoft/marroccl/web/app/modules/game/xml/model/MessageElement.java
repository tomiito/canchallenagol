package ar.com.ironsoft.marroccl.web.app.modules.game.xml.model;

import java.util.Date;

import org.dom4j.Element;

import ar.com.ironsoft.marroccl.web.app.modules.game.model.Message;

/**
 *
 * @author Tomas de Priede
 */
public class MessageElement extends BaseElement implements Message {

    private final Element element;
    private Integer messageId;
    private String comment;
    private Date lastModified;
    private Integer period;
    private Integer minute;
    private Integer second;
    private String time;
    private String type;

    public MessageElement(Element element) {
        this.element = element;
    }

    public void parseAttributes() {
        messageId = parseInteger(element.attributeValue("id"));
        comment = element.attributeValue("comment");
        lastModified = parseDate(element.attributeValue("last_modified"));
        period = parseInteger(element.attributeValue("period"));
        minute = parseInteger(element.attributeValue("minute"));
        second = parseInteger(element.attributeValue("second"));
        type = element.attributeValue("type");
        time = element.attributeValue("time");
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
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
        return String.valueOf(messageId);
    }

    @Override
    public void setId(String id) {
        setMessageId(parseInteger(id));
    }
}
