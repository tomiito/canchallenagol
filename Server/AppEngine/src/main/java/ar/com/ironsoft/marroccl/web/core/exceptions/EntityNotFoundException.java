package ar.com.ironsoft.marroccl.web.core.exceptions;

/**
 * User: Tomas de Priede Date: 11/19/13 Time: 11:02 PM
 */
public class EntityNotFoundException extends DatastoreException {

    public EntityNotFoundException(Throwable t) {
        super(t);
    }

    public EntityNotFoundException(String msg) {
        super(msg);
    }
}
