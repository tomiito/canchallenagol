package ar.com.ironsoft.marroccl.web.core.exceptions;

/**
 * User: Tomas de Priede Date: 11/19/13 Time: 11:02 PM
 */
public class DatastoreException extends ApplicationException {

    public DatastoreException(Throwable t) {
        super(t);
    }

    public DatastoreException(String msg) {
        super(msg);
    }
}
