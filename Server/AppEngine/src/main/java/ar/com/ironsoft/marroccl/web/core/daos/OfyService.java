package ar.com.ironsoft.marroccl.web.core.daos;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * User: Tomas de Priede Date: 8/5/14 Time: 8:42 AM
 */
public class OfyService {

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
