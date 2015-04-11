package ar.com.ironsoft.marroccl.web.core.constants;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.utils.SystemProperty;

/**
 * @author Tomas de Priede
 */
public class SharedConstants {

    private static final Logger logger = Logger.getLogger(SharedConstants.class
            .getSimpleName());
    public static final char LAST_UNICODE_CHARACTER = '\ufffd';
    public static final String EMPTY_STRING = "";
    public static final String INDEX_JSP_URL = "/WEB-INF/pages/index.jsp";
    public static final String API_KEY = "AIzaSyDhZTKbHg1HBM7Cx5Bncm8OsYwW-O9X66U";
    public static final String UTF_8 = "UTF-8";
    //
    private static ResourceBundle appProperties;
    public static String APP_VERSION;
    public static String APP_URL;
    public static String MAIL_CONTACT;
    static {
        try {
            appProperties = ResourceBundle.getBundle("app");
            APP_VERSION = appProperties.getString("app.version");
            APP_URL = appProperties.getString("app.url");
            MAIL_CONTACT = appProperties.getString("app.email.contact");
        } catch (MissingResourceException e) {
            logger.log(Level.WARNING,
                    "No app.properties found. Using default configuration", e);
        }
    }

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
