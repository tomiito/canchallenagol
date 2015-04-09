package ar.com.ironsoft.marroccl.web.core.model.paging;

import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.google.api.server.spi.config.ApiTransformer;

/**
 * @author Tomas de Priede
 */
@ApiTransformer(CursorTransformer.class)
public class Cursor {

    private Integer index;
    private String cursor;

    public Cursor() {
    }

    public Cursor(Integer index, String cursor) {
        this.index = index;
        this.cursor = cursor;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

    public static String findNextAction(Set<Cursor> cursors, Integer start) {
        if (cursors != null) {
            for (Cursor c : cursors) {
                if (c.getIndex().equals(start)) {
                    return c.getCursor();
                }
            }
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Cursor rhs = (Cursor) obj;
        return new EqualsBuilder().append(cursor, rhs.cursor)
                .append(index, rhs.index).isEquals();
    }

    public int hashCode() {
        // you pick a hard-coded, randomly chosen, non-zero, odd number
        // ideally different for each class
        return new HashCodeBuilder(27, 37).append(cursor).append(index)
                .toHashCode();
    }
}
