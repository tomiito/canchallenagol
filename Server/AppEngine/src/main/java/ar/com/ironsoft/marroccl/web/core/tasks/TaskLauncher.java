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
    public static final String QUEUE_NAME = "gcm";

    public void launchTask(Class<? extends HttpServlet> task) {
        launchTask(task, null);
    }

    public void launchTask(Class<? extends HttpServlet> task,
            TaskParameter... taskParameters) {
        Queue q = QueueFactory.getQueue(QUEUE_NAME);
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

    public void launchTask(Class<? extends TaskServlet> task, byte[] serialized) {
        Queue q = QueueFactory.getQueue(QUEUE_NAME);
        TaskOptions taskOptions = TaskOptions.Builder.withUrl(URLPaths
                .findTerminatedPath(task));
        taskOptions.method(TaskOptions.Method.POST);
        taskOptions.payload(serialized, APPLICATION_X_JAVA_SERIALIZED_OBJECT);
        q.add(taskOptions);
    }
}
