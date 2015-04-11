package ar.com.ironsoft.marroccl.web.app.modules.game.tasks;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.com.ironsoft.marroccl.web.app.modules.game.daos.VideoUrlDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.model.VideoUrl;
import ar.com.ironsoft.marroccl.web.app.modules.game.xml.model.Commentary;
import ar.com.ironsoft.marroccl.web.core.tasks.TaskServlet;
import ar.com.ironsoft.marroccl.web.guice.base.BasePath;
import ar.com.ironsoft.marroccl.web.guice.base.RelativePath;

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
@BasePath("/game/url/")
@RelativePath("findUrl")
public class FindUrlTaskServlet extends TaskServlet {

    private Logger logger = Logger.getLogger(FindUrlTaskServlet.class
            .getSimpleName());
    private VideoUrlDao videoUrlDao;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.log(Level.INFO,
                "Starting task" + FindUrlTaskServlet.class.getSimpleName());
        if (doInitialTaskCheck(req, resp)) {
            logger.log(Level.INFO, "Pass initial checking");
            String gameId = req.getParameter(Commentary.GAME_ID);
            String startUrl = req.getParameter("startUrl");
            Integer videoMinute = Integer.parseInt(req.getParameter("video"));
            Integer hour = Integer.parseInt(req.getParameter("hour"));
            Integer minute = Integer.parseInt(req.getParameter("minute"));
            //
            //
            for (int j = 0; j < 99; j++) {
                boolean retry = false;
                int times = 1;
                String url = null;
                do {
                    try {
                        logger.log(Level.FINE, String.format(
                                "Video: %d Fetching url Times: %d",
                                videoMinute, times));
                        url = findVideo(startUrl, hour, minute, j);
                        retry = false;
                        if (url != null) {
                            videoUrlDao.save(new VideoUrl(gameId, url,
                                    videoMinute));
                            break;
                        }
                    } catch (SocketTimeoutException e) {
                        logger.log(Level.WARNING,
                                "Failed to get url, retrying...");
                        retry = true;
                        times++;
                    }
                } while (retry == true && times <= 3);
                if (url != null) {
                    break;
                }
            }
        }
    }

    public String findVideo(String startUrl, int hour, int minute, int seconds)
            throws IOException {
        String urlString = startUrl.replace("{0}",
                String.format("%02d%02d%02d", hour, minute, seconds));
        logger.log(Level.INFO, "C:" + urlString);
        URL url = new URL(urlString);
        HTTPRequest gaeRequest = new HTTPRequest(url, HTTPMethod.GET);
        gaeRequest.getFetchOptions().setDeadline(20.0);
        HTTPResponse response = URLFetchServiceFactory.getURLFetchService()
                .fetch(gaeRequest);
        //
        boolean video = 200 == response.getResponseCode();
        if (video) {
            logger.log(Level.INFO, "---> " + urlString);
            return urlString;
        }
        return null;
    }

    @Inject
    public void setVideoUrlDao(VideoUrlDao videoUrlDao) {
        this.videoUrlDao = videoUrlDao;
    }
}
