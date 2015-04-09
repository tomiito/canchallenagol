package ar.com.ironsoft.marroccl.web.core.tasks;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tomas de Priede
 */
public abstract class TaskServlet extends HttpServlet {

    private Logger logger = Logger.getLogger(TaskServlet.class.getSimpleName());
    private static final String HEADER_QUEUE_COUNT = "X-AppEngine-TaskRetryCount";
    private static final String HEADER_QUEUE_NAME = "X-AppEngine-QueueName";
    private static final int MAX_RETRY = 3;

    protected boolean doInitialTaskCheck(HttpServletRequest req,
            HttpServletResponse resp) throws IOException {
        boolean valid = true;
        if (req.getHeader(HEADER_QUEUE_NAME) == null) {
            throw new IOException("Missing header " + HEADER_QUEUE_NAME);
        }
        String retryCountHeader = req.getHeader(HEADER_QUEUE_COUNT);
        logger.fine("retry count: " + retryCountHeader);
        if (retryCountHeader != null) {
            int retryCount = Integer.parseInt(retryCountHeader);
            if (retryCount > MAX_RETRY) {
                logger.severe("Too many retries, dropping task");
                taskDone(resp);
                valid = false;
            }
        }
        return valid;
    }

    /**
     * Indicates to App Engine that this task should be retried.
     */
    protected void retryTask(HttpServletResponse resp) {
        resp.setStatus(500);
    }

    /**
     * Indicates to App Engine that this task is done.
     */
    protected void taskDone(HttpServletResponse resp) {
        resp.setStatus(200);
    }
}
