package ar.com.ironsoft.marroccl.web.app.modules.messages.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.com.ironsoft.marroccl.web.app.modules.messages.model.SimpleMessage;
import ar.com.ironsoft.marroccl.web.app.modules.messages.tasks.SendAllMessageTask;
import ar.com.ironsoft.marroccl.web.core.servlets.BaseServlet;
import ar.com.ironsoft.marroccl.web.core.tasks.TaskLauncher;
import ar.com.ironsoft.marroccl.web.core.utils.ObjectSerializationUtils;
import ar.com.ironsoft.marroccl.web.guice.base.BasePath;
import ar.com.ironsoft.marroccl.web.guice.base.RelativePath;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Tomas de Priede
 */
@Singleton
@BasePath("/messages/")
@RelativePath("sendAll")
public class SendAllMessageServlet extends BaseServlet {

    private TaskLauncher taskLauncher;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String message = String.valueOf(req.getParameter("message"));
        Preconditions.checkNotNull(message);
        //
        SimpleMessage simpleMessage = new SimpleMessage();
        simpleMessage.setMessage(message);
        //
        taskLauncher.launchTask(SendAllMessageTask.class,
                ObjectSerializationUtils.serialize(simpleMessage));
        //
        setSuccess(resp);
    }

    @Inject
    public void setTaskLauncher(TaskLauncher taskLauncher) {
        this.taskLauncher = taskLauncher;
    }
}
