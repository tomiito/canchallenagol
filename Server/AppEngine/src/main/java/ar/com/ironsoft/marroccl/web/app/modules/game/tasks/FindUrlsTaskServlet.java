package ar.com.ironsoft.marroccl.web.app.modules.game.tasks;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.com.ironsoft.marroccl.web.app.modules.config.model.ConfigHolder;
import ar.com.ironsoft.marroccl.web.app.modules.game.daos.VideoUrlDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.xml.model.Commentary;
import ar.com.ironsoft.marroccl.web.core.tasks.TaskLauncher;
import ar.com.ironsoft.marroccl.web.core.tasks.TaskParameter;
import ar.com.ironsoft.marroccl.web.core.tasks.TaskServlet;
import ar.com.ironsoft.marroccl.web.guice.base.BasePath;
import ar.com.ironsoft.marroccl.web.guice.base.RelativePath;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Tomas de Priede
 */
@Singleton
@BasePath("/game/url/")
@RelativePath("findUrls")
public class FindUrlsTaskServlet extends TaskServlet {

    private Logger logger = Logger.getLogger(FindUrlsTaskServlet.class
            .getSimpleName());
    private TaskLauncher taskLauncher;
    private VideoUrlDao videoUrlDao;
    private ConfigHolder configHolder;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.log(Level.INFO,
                "Starting task" + FindUrlsTaskServlet.class.getSimpleName());
        if (doInitialTaskCheck(req, resp)) {
            logger.log(Level.INFO, "Pass initial checking");
            //
            logger.log(Level.INFO, "Delete old videoUrls");
            videoUrlDao.deleteAll();
            //
            logger.log(Level.INFO, "Launch child tasks");
            //
            String gameId = req.getParameter(Commentary.GAME_ID);
            //
            String startUrl = configHolder.getVideoUrl();
            int totalVideos = configHolder.getTotalVideos();
            int hour = configHolder.getStartHour();
            int minute = configHolder.getStartMinute();
            //
            for (int i = 0; i <= totalVideos; i++) {
                //
                taskLauncher.launchTask(TaskLauncher.QUEUE_FIND_URL,
                        FindUrlTaskServlet.class, //
                        new TaskParameter(Commentary.GAME_ID, gameId), //
                        new TaskParameter("video", String.valueOf(i)), //
                        new TaskParameter("startUrl", startUrl), //
                        new TaskParameter("hour", String.valueOf(hour)), //
                        new TaskParameter("minute", String.valueOf(minute)) //
                        );
                minute++;
                if (minute == 60) {
                    minute = 0;
                    hour++;
                }
            }
        }
    }

    @Inject
    public void setConfigHolder(ConfigHolder configHolder) {
        this.configHolder = configHolder;
    }

    @Inject
    public void setVideoUrlDao(VideoUrlDao videoUrlDao) {
        this.videoUrlDao = videoUrlDao;
    }

    @Inject
    public void setTaskLauncher(TaskLauncher taskLauncher) {
        this.taskLauncher = taskLauncher;
    }
}
