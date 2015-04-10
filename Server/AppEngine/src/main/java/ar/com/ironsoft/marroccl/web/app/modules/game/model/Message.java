package ar.com.ironsoft.marroccl.web.app.modules.game.model;

import java.util.Date;

import ar.com.ironsoft.marroccl.web.core.model.BaseModel;

/**
 * @author Tomas de Priede
 */
public interface Message extends BaseModel {

    public Integer getMessageId();

    public void setMessageId(Integer messageId);

    public String getComment();

    public void setComment(String comment);

    public Date getLastModified();

    public void setLastModified(Date lastModified);

    public Integer getPeriod();

    public void setPeriod(Integer period);

    public Integer getMinute();

    public void setMinute(Integer minute);

    public Integer getSecond();

    public void setSecond(Integer second);

    public String getTime();

    public void setTime(String time);

    public String getType();

    public void setType(String type);
}
