/**
 * 
 */
package ar.com.ironsoft.marroccl.web.core.daos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import ar.com.ironsoft.marroccl.web.core.exceptions.EntityNotFoundException;
import ar.com.ironsoft.marroccl.web.core.model.BaseModel;
import ar.com.ironsoft.marroccl.web.core.model.paging.PagingResult;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterable;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.appengine.api.memcache.MemcacheServiceException;
import com.google.common.collect.Lists;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.LoadResult;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.cmd.Query;

/**
 * Base class for all FlashPanel data access objects (DAO). Provides a common
 * constructor ObjectifyFactory injection point for extending DAOs. Provides a
 * common method to create DAOs with options. Class also provides generic
 * datastore CRUD operations.
 * 
 * @author Tomas de Priede
 * @param <T>
 *            must be a registered type with the passed in objectify factory
 */
public abstract class BaseDaoObjectifyImpl<T extends BaseModel> implements
        BaseDao<T> {

    /**
     * The factory must be injected by the implementing class
     */
    public BaseDaoObjectifyImpl() {
        super();
    }

    /**
     * save or update entity in datastore entity must be of a type registered
     * with the injected objectify factory
     * 
     * @param entity
     *            must not be null
     * @return the Key of the saved object
     */
    @Override
    public Key<T> save(T entity) {
        return OfyService.ofy().save().entity(entity).now();
    }

    /**
     * save or update entities in datastore entities must be of a type
     * registered with the injected objectify factory
     * 
     * @param entities
     * @return a map of the saved entities mapped to their datastore keys
     */
    @Override
    public Map<Key<T>, T> saveAll(Iterable<T> entities) {
        return OfyService.ofy().save().entities(entities).now();
    }

    /**
     * get object of type clazz that is stored in the datastore under the param
     * id clazz must be of a type registered with the injected objectify factory
     * 
     * @param clazz
     *            the returned value type
     * @param id
     * @return the object of type clazz that matches on the id
     * @throws ar.com.ironsoft.marroccl.web.core.exceptions.EntityNotFoundException
     *             thrown if no entity object could be found
     */
    @Override
    public T get(Class<T> clazz, String id) throws EntityNotFoundException {
        return get(clazz, id, true);
    }

    private T get(Class<T> clazz, String id, boolean retry)
            throws EntityNotFoundException {
        T result = null;
        try {
            result = OfyService.ofy().load().key(Key.create(clazz, id)).safe();
        } catch (NotFoundException nfe) {
            throw new EntityNotFoundException(nfe);
        } catch (MemcacheServiceException mse) {
            if (retry) {
                OfyService.ofy().clear();
                get(clazz, id, false);
            }
        }
        return result;
    }

    /**
     * 
     * get object of type clazz that is stored in the datastore under the param
     * id clazz must be of a type registered with the injected objectify factory
     * 
     * @param clazz
     *            the returned value type
     * @param id
     * @return the object of type clazz that matches on the id
     * @throws EntityNotFoundException
     *             thrown if no entity object could be found
     * @author Tomas de Priede
     */
    @Override
    public LoadResult<T> getAsync(Class<T> clazz, String id)
            throws EntityNotFoundException {
        return OfyService.ofy().load().key(Key.create(clazz, id));
    }

    /**
     * get object of type clazz that is stored in the datastore under the param
     * id clazz must be of a type registered with the injected objectify factory
     * 
     * @param clazz
     *            the returned value type
     * @param id
     * @return the object of type clazz that matches on the id
     * @throws EntityNotFoundException
     *             thrown if no entity object could be found
     */
    @Override
    public T get(Class<T> clazz, Long id) throws EntityNotFoundException {
        T result = null;
        try {
            result = OfyService.ofy().load().key(Key.create(clazz, id)).safe();
        } catch (NotFoundException nfe) {
            throw new EntityNotFoundException(nfe);
        }
        return result;
    }

    /**
     * get entities from datastore that match against the passed in collection
     * of ids
     * 
     * @param ids
     *            the set of String or Long ids matching against those entities
     *            to be retrieved from the datastore
     * @return all entities that match on the collection of ids. no error is
     *         thrown for entities not found in datastore.
     */
    @Override
    public Map<String, T> get(Class<T> paramClass, Iterable<String> ids) {
        Map<String, T> result = OfyService.ofy().load().type(paramClass)
                .ids(ids);
        return result;
    }

    /**
     * get entities from datastore that match against the passed in collection
     * of ids
     * 
     * @param ids
     *            the set of String or Long ids matching against those entities
     *            to be retrieved from the datastore
     * @return all entities that match on the collection of ids. no error is
     *         thrown for entities not found in datastore.
     */
    @Override
    public Map<Long, T> gets(Class<T> paramClass, Iterable<Long> ids) {
        Map<Long, T> result = OfyService.ofy().load().type(paramClass).ids(ids);
        return OfyService.ofy().load().type(paramClass).ids(ids);
    }

    /**
     * Get entities from datastore that match against the passed in collection
     * of ids buts using chunks of a determinated size to avoid to retrieve all
     * the entities in just one query.
     * 
     * @author Tomas de Priede
     * @since Jan-24-2013
     * 
     * @param paramClass
     * @param keys
     *            the list of keys of all the entities to be retrieved
     * @param chunkSize
     *            the max number of elements to be retrieved per query
     * @return
     */
    @Override
    public Map<Long, T> getsInChunks(Class<T> paramClass, List<Long> keys,
            int chunkSize) {
        List<List<Long>> longsList = Lists.partition(keys, chunkSize);
        Map<Long, T> entities = new HashMap<Long, T>();
        for (List<Long> partitionedList : longsList) {
            entities.putAll(gets(paramClass, partitionedList));
        }
        return entities;
    }

    /**
     * get entities from datastore that match against the passed in collection
     * of keys
     * 
     * @param keys
     *            the set of keys matching against those entities to be
     *            retrieved from the datastore
     * @return all entities that match on the collection of keys. no error is
     *         thrown for entities not found in datastore.
     */
    @Override
    public Map<Key<T>, T> get(Iterable<Key<T>> keys) {
        Map<Key<T>, T> result = OfyService.ofy().load().keys(keys);
        return result;
    }

    /**
     * delete object of type clazz that is stored in the datastore under the
     * param id clazz must be of a type registered with the injected objectify
     * factory
     * 
     * @param clazz
     *            the returned value type
     * @param id
     */
    @Override
    public void delete(Class<T> clazz, String id) {
        OfyService.ofy().delete().type(clazz).id(id).now();
    }

    /**
     * delete object of type clazz that is stored in the datastore under the
     * param id clazz must be of a type registered with the injected objectify
     * factory
     * 
     * @param clazz
     *            the returned value type
     * @param id
     */
    @Override
    public void delete(Class<T> clazz, Long id) {
        OfyService.ofy().delete().type(clazz).id(id).now();
    }

    /**
     * delete entities from datastore that match against the passed in
     * collection entities must be of a type registered with the injected
     * objectify factory
     * 
     * @param entities
     */
    @Override
    public void deleteEntities(Iterable<T> entities) {
        OfyService.ofy().delete().entities(entities).now();
    }

    /**
     * delete entities from datastore that match against the passed in
     * collection keys must be of a type string with the injected objectify
     * factory
     * 
     * the keys to delete
     */
    @Override
    public void deleteEntitiesByKey(Class<T> clazz, Iterable<String> stringKeys) {
        OfyService.ofy().delete().type(clazz).ids(stringKeys).now();
    }

    @Override
    public Integer getCount(Class<T> clazz) {
        return getCount(clazz, OfyService.ofy().load().type(clazz));
    }

    @Override
    public Integer getCount(Class<T> clazz, Query<T> loadType) {
        Integer count = loadType.limit(5000).count();
        if (count == 5000) {
            int pageSize = 1000;
            Query query = loadType.limit(pageSize);
            String cursor = null;
            count = 0;
            do {
                if (StringUtils.isNotEmpty(cursor)) {
                    query = query.startAt(Cursor.fromWebSafeString(cursor));
                }
                QueryResultIterator iterator = query.keys().iterator();
                String newCursor = null;
                int pageCount = 0;
                while (iterator.hasNext()) {
                    pageCount++;
                    iterator.next();
                }
                count += pageCount;
                if (pageCount == pageSize) {
                    Cursor c = iterator.getCursor();
                    if (c != null) {
                        newCursor = c.toWebSafeString();
                    }
                }
                if (newCursor != null && !newCursor.equals(cursor)) {
                    cursor = newCursor;
                } else {
                    cursor = null;
                }
            } while (cursor != null);
        }
        return count;
    }

    @Override
    public PagingResult<T> getPageOfAll(Class<T> clazz, Integer pageSize,
            String cursor) {
        return getPagingResult(OfyService.ofy().load().type(clazz), pageSize,
                cursor);
    }

    protected PagingResult<T> getPagingResult(Query<T> query, Integer pageSize,
            String cursor) {
        query = query.limit(pageSize);
        if (StringUtils.isNotEmpty(cursor)) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<T> iterator = query.iterator();
        List<T> list = new ArrayList<T>();
        String newCursor = null;
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        if (list.size() == pageSize) {
            Cursor c = iterator.getCursor();
            if (c != null) {
                String webSafeCursor = c.toWebSafeString();
                if (webSafeCursor != null && !webSafeCursor.equals(cursor)) {
                    newCursor = webSafeCursor;
                }
            }
        }
        PagingResult<T> pagingResult = new PagingResult<T>(newCursor, list);
        return pagingResult;
    }

    protected PagingResult<String> getPagingResultKeys(Query<T> query,
            Integer pageSize, String cursor) {
        query = query.limit(pageSize);
        if (StringUtils.isNotEmpty(cursor)) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterable<Key<T>> iterable = query.keys();
        QueryResultIterator<Key<T>> iterator = iterable.iterator();
        List<String> list = new ArrayList<String>();
        String newCursor = null;
        while (iterator.hasNext()) {
            list.add(iterator.next().getName());
        }
        if (list.size() == pageSize) {
            Cursor c = iterator.getCursor();
            if (c != null) {
                String webSafeCursor = c.toWebSafeString();
                if (webSafeCursor != null && !webSafeCursor.equals(cursor)) {
                    newCursor = webSafeCursor;
                }
            }
        }
        PagingResult<String> pagingResult = new PagingResult<String>(newCursor,
                list);
        return pagingResult;
    }
}
