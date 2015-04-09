package ar.com.ironsoft.marroccl.web.core.daos;

import ar.com.ironsoft.marroccl.web.core.model.BaseModel;
import ar.com.ironsoft.marroccl.web.core.model.paging.PagingResult;

/**
 * @author Tomas de Priede
 */
public interface PagingDatasource<T extends BaseModel> {

    /**
     * fetch an unfiltered page of T
     * 
     * @param clazz
     *            the class of T
     * @param pageSize
     *            the page size
     * @param cursor
     *            the location within the overall query results to return
     * @return
     */
    public PagingResult<T> getPageOfAll(Class<T> clazz, Integer pageSize,
            String cursor);
}
