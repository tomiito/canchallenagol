package ar.com.ironsoft.marroccl.web.app.modules.messages.endpoints;

import java.io.IOException;

import ar.com.ironsoft.marroccl.web.app.modules.messages.model.SimpleMessage;
import ar.com.ironsoft.marroccl.web.app.modules.messages.tasks.SendAllMessageTask;
import ar.com.ironsoft.marroccl.web.core.tasks.TaskLauncher;
import ar.com.ironsoft.marroccl.web.core.utils.ObjectSerializationUtils;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.inject.Inject;

/**
 * @author Tomas de Priede
 */
@Api(name = "messagesApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "ironsoft", ownerName = "MarrocCL", packagePath = "app/messages"))
public class MessagesEndpointApi {

    private TaskLauncher taskLauncher;

    @ApiMethod(httpMethod = "post")
    public void sendAll(@Named("message") String message) throws IOException {
        SimpleMessage simpleMessage = new SimpleMessage();
        simpleMessage.setMessage(message);
        //
        taskLauncher.launchTask(SendAllMessageTask.class,
                ObjectSerializationUtils.serialize(simpleMessage));
    }

    @Inject
    public void setTaskLauncher(TaskLauncher taskLauncher) {
        this.taskLauncher = taskLauncher;
    }
}
