package ar.com.ironsoft.marroccl.web.app.modules.messages.endpoints;

import java.io.IOException;

import ar.com.ironsoft.marroccl.web.app.modules.messages.model.VideoMessage;
import ar.com.ironsoft.marroccl.web.app.modules.messages.tasks.SendAllMessageTask;
import ar.com.ironsoft.marroccl.web.core.tasks.TaskLauncher;
import ar.com.ironsoft.marroccl.web.core.utils.ObjectSerializationUtils;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.Nullable;
import com.google.inject.Inject;

/**
 * @author Tomas de Priede
 */
@Api(name = "messagesApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "ironsoft", ownerName = "MarrocCL", packagePath = "app/messages"))
public class MessagesEndpointApi {

    private TaskLauncher taskLauncher;

    @ApiMethod(httpMethod = "post")
    public void videoSendAll(@Named("message") String message, //
            @Nullable @Named("videoLink") String videoLink, //
            @Nullable @Named("gifLink") String gifLink, //
            @Nullable @Named("thumbnailLink") String thumbnailLink)
            throws IOException {
        VideoMessage videoMessage = new VideoMessage();
        videoMessage.setMessage(message);
        videoMessage.setVideoLink(videoLink);
        videoMessage.setGifLink(gifLink);
        videoMessage.setThumbnailLink(thumbnailLink);
        //
        taskLauncher.launchTask(SendAllMessageTask.class,
                ObjectSerializationUtils.serialize(videoMessage));
    }

    @Inject
    public void setTaskLauncher(TaskLauncher taskLauncher) {
        this.taskLauncher = taskLauncher;
    }
}
