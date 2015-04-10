package ar.com.ironsoft.marroccl.web.app.modules.game.cron;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.com.ironsoft.marroccl.web.app.modules.game.model.Commentary;
import ar.com.ironsoft.marroccl.web.app.modules.game.xml.GameXmlService;
import ar.com.ironsoft.marroccl.web.core.constants.SharedConstants;
import ar.com.ironsoft.marroccl.web.core.servlets.BaseServlet;
import ar.com.ironsoft.marroccl.web.guice.base.BasePath;
import ar.com.ironsoft.marroccl.web.guice.base.RelativePath;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Parse xml after 1 minutes
 *
 * @author Tomas de Priede
 */
@Singleton
@BasePath("/cron/")
@RelativePath("parseGame")
public class ParseGameCronServlet extends BaseServlet {

    private Logger logger = Logger.getLogger(ParseGameCronServlet.class
            .getSimpleName());
    private GameXmlService gameXmlService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        // Receive game
        URL url = new URL(SharedConstants.XML_URL);
        HTTPRequest gaeRequest = new HTTPRequest(url, HTTPMethod.GET);
        gaeRequest.addHeader(new HTTPHeader("accept", "application/xml"));
        HTTPResponse xmlResponse = URLFetchServiceFactory.getURLFetchService()
                .fetch(gaeRequest);
        //
        String xml = new String(xmlResponse.getContent(), SharedConstants.UTF_8);
        Commentary commentary = gameXmlService.parseGameXml(xml);
        setSuccess(resp);
    }

    @Inject
    public void setGameXmlService(GameXmlService gameXmlService) {
        this.gameXmlService = gameXmlService;
    }
}
