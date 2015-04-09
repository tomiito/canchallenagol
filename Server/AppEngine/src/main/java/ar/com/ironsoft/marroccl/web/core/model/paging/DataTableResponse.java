package ar.com.ironsoft.marroccl.web.core.model.paging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Tomas de Priede
 */
public class DataTableResponse implements Serializable {

    private List data = new ArrayList<>();
    private Set<Cursor> cursors = new HashSet<>();

    public DataTableResponse(Integer length, Integer start, PagingResult page,
            Set<Cursor> cursors, String oldCursor) {
        this.data = page.getResultList();
        if (cursors == null) {
            cursors = new HashSet();
        }
        cursors.add(new Cursor(start, oldCursor));
        cursors.add(new Cursor(start + length, page.getCursor()));
        this.cursors = cursors;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public Set<Cursor> getCursors() {
        return cursors;
    }

    public void setCursors(Set<Cursor> cursors) {
        this.cursors = cursors;
    }
}
