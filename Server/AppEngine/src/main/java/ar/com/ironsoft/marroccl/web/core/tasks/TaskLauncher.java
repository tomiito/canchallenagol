package ar.com.ironsoft.marroccl.web.core.tasks;

import javax.servlet.http.HttpServlet;

import ar.com.ironsoft.marroccl.web.guice.base.URLPaths;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.inject.Singleton;

/**
 * @author Tomas de Priede
 */
@Singleton
public class TaskLauncher {

    public static final String APPLICATION_X_JAVA_SERIALIZED_OBJECT = "application/x-java-serialized-object";
    public static final String QUEUE_GCM = "gcm";
    public static final String QUEUE_GCM_PAGED = "gcm-paged";
    public static final String QUEUE_FIND_URL = "url-find";
    public static final String QUEUE_FIND_URL_PAGED = "url-find-paged";


    public void launchTask(String queueName, Class<? extends HttpServlet> task,
            TaskParameter... taskParameters) {
        Queue q = QueueFactory.getQueue(queueName);
        TaskOptions taskOptions = TaskOptions.Builder.withUrl(URLPaths
                .findTerminatedPath(task));
        //
        if (taskParameters != null) {
            for (TaskParameter p : taskParameters) {
                taskOptions.param(p.getName(), p.getValue());
            }
        }
        //
        taskOptions.countdownMillis(1000).method(TaskOptions.Method.POST);
        q.add(taskOptions);
    }

    public void launchTask(String queueName,Class<? extends TaskServlet> task, byte[] serialized) {
        Queue q = QueueFactory.getQueue(queueName);
        TaskOptions taskOptions = TaskOptions.Builder.withUrl(URLPaths
                .findTerminatedPath(task));
        taskOptions.method(TaskOptions.Method.POST);
        taskOptions.payload(serialized, APPLICATION_X_JAVA_SERIALIZED_OBJECT);
        q.add(taskOptions);
    }
}
