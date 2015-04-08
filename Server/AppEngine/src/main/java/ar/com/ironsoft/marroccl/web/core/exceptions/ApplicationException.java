package ar.com.ironsoft.marroccl.web.core.exceptions;

/**
 * User: Tomas de Priede Date: 11/19/13 Time: 10:59 PM
 */
public class ApplicationException extends RuntimeException {

    public ApplicationException(Throwable t) {
        super(t);
    }

    public ApplicationException() {
    }

    public ApplicationException(String msg) {
        super(msg);
    }

    public ApplicationException(String msg, Throwable t) {
        super(msg, t);
    }
}
