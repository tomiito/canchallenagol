package ar.com.ironsoft.marroccl.web.app.modules.game.services;

import java.io.IOException;
import java.net.URL;

import ar.com.ironsoft.marroccl.web.app.modules.config.daos.ConfigHolderDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.xml.GameXmlService;
import ar.com.ironsoft.marroccl.web.app.modules.game.xml.model.Commentary;
import ar.com.ironsoft.marroccl.web.core.constants.SharedConstants;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Tomas de Priede
 */
@Singleton
public class GameService {

    private GameXmlService gameXmlService;
    private CommentaryService commentaryService;
    private ConfigHolderDao configHolderDao;
    private ScoreService scoreService;

    public void parseGame() throws IOException {
        // Receive game
        URL url = new URL(configHolderDao.getConfig().getXmlUrl());
        HTTPRequest gaeRequest = new HTTPRequest(url, HTTPMethod.GET);
        gaeRequest.addHeader(new HTTPHeader("accept", "application/xml"));
        HTTPResponse xmlResponse = URLFetchServiceFactory.getURLFetchService()
                .fetch(gaeRequest);
        //
        String xml = new String(xmlResponse.getContent(), SharedConstants.UTF_8);
        Commentary commentary = gameXmlService.parseGameXml(xml);
        commentaryService.saveCommentary(commentary);
        scoreService.processGoals(commentary);
    }

    @Inject
    public void setScoreService(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @Inject
    public void setConfigHolderDao(ConfigHolderDao configHolderDao) {
        this.configHolderDao = configHolderDao;
    }

    @Inject
    public void setCommentaryService(CommentaryService commentaryService) {
        this.commentaryService = commentaryService;
    }

    @Inject
    public void setGameXmlService(GameXmlService gameXmlService) {
        this.gameXmlService = gameXmlService;
    }
}
