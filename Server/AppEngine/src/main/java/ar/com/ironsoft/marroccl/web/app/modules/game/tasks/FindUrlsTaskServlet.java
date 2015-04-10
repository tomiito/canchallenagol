package ar.com.ironsoft.marroccl.web.app.modules.game.tasks;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    public static final String FIRST_VIDEO_URL = "https://s3.amazonaws.com/historico.lanacion.com.ar/Partidos/TYC.20150331_{0}.mp4";
    private Logger logger = Logger.getLogger(FindUrlsTaskServlet.class
            .getSimpleName());
    private TaskLauncher taskLauncher;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.log(Level.INFO,
                "Starting task" + FindUrlsTaskServlet.class.getSimpleName());
        if (doInitialTaskCheck(req, resp)) {
            logger.log(Level.INFO, "Pass initial checking");
            //
            String gameId = req.getParameter(Commentary.GAME_ID);
            //
            String startUrl = FIRST_VIDEO_URL;
            int totalVideos = 3;
            int hour = 21;
            int minute = 1;
            //
            for (int i = 0; i < totalVideos; i++) {
                //
                taskLauncher.launchTask(FindUrlTaskServlet.class,
                        new TaskParameter(Commentary.GAME_ID, gameId), //
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
    public void setTaskLauncher(TaskLauncher taskLauncher) {
        this.taskLauncher = taskLauncher;
    }
}
