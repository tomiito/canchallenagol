package ar.com.ironsoft.marroccl.web.core.constants;

import com.google.appengine.api.utils.SystemProperty;

/**
 * @author Tomas de Priede
 */
public class SharedConstants {

    public static final char LAST_UNICODE_CHARACTER = '\ufffd';
    public static final String EMPTY_STRING = "";
    public static final String INDEX_JSP_URL = "/WEB-INF/pages/index.jsp";
    public static final String API_KEY = "AIzaSyDhZTKbHg1HBM7Cx5Bncm8OsYwW-O9X66U";

    //
    public static boolean isDevelopmentServer() {
        SystemProperty.Environment.Value server = SystemProperty.environment
                .value();
        return server == SystemProperty.Environment.Value.Development;
    }

    public static boolean isProductionServer() {
        SystemProperty.Environment.Value server = SystemProperty.environment
                .value();
        return server == SystemProperty.Environment.Value.Production;
    }
}
