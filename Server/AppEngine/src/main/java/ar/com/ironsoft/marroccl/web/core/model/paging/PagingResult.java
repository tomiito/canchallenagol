package ar.com.ironsoft.marroccl.web.core.model.paging;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a page of data. There are two fields combined to represent a page
 * of data: <br />
 * 1. cursor: a string cursor necesary for accessing the next page of data <br />
 * 2. resultList: a list of data of the current page
 * 
 * @author Tomas de Priede
 * @param <T>
 */
@SuppressWarnings("serial")
public class PagingResult<T extends Object> implements Serializable {

    private String cursor;
    private List<T> resultList;

    public PagingResult() {
        super();
    }

    public PagingResult(String cursor, List<T> resultList) {
        super();
        this.cursor = cursor;
        this.resultList = resultList;
    }

    public PagingResult(PagingResult<T> result) {
        this(result.cursor, result.resultList);
    }

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }
}
