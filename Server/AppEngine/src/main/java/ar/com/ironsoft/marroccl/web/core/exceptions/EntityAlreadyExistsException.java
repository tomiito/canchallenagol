package ar.com.ironsoft.marroccl.web.core.exceptions;

/**
 * User: Tomas de Priede Date: 11/28/13 Time: 11:49 AM
 */
public class EntityAlreadyExistsException extends ApplicationException {

    public EntityAlreadyExistsException(String email, Throwable t) {
        super(email, t);
    }

    public EntityAlreadyExistsException(String email) {
        super(email);
    }
}
