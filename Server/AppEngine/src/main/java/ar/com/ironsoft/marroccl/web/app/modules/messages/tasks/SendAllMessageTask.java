package ar.com.ironsoft.marroccl.web.app.modules.messages.tasks;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.com.ironsoft.marroccl.web.app.modules.devices.daos.DeviceDao;
import ar.com.ironsoft.marroccl.web.app.modules.devices.model.Device;
import ar.com.ironsoft.marroccl.web.app.modules.messages.model.MulticastMessage;
import ar.com.ironsoft.marroccl.web.app.modules.messages.model.SimpleMessage;
import ar.com.ironsoft.marroccl.web.app.modules.messages.services.MulticastService;
import ar.com.ironsoft.marroccl.web.core.model.paging.PagingResult;
import ar.com.ironsoft.marroccl.web.core.tasks.TaskLauncher;
import ar.com.ironsoft.marroccl.web.core.tasks.TaskServlet;
import ar.com.ironsoft.marroccl.web.core.utils.ObjectSerializationUtils;
import ar.com.ironsoft.marroccl.web.guice.base.BasePath;
import ar.com.ironsoft.marroccl.web.guice.base.RelativePath;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * send a multicast message using JSON <br />
 * must split in chunks of 1000 devices (GCM limit)
 *
 * @author Tomas de Priede
 */
@Singleton
@BasePath("/tasks/messages/")
@RelativePath("sendAll")
public class SendAllMessageTask extends TaskServlet {

    private static final Integer PAGE_SIZE = 1000;
    private Logger logger = Logger.getLogger(SendAllMessageTask.class
            .getSimpleName());
    private DeviceDao deviceDao;
    private MulticastService multicastService;
    private TaskLauncher taskLauncher;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (doInitialTaskCheck(req, resp)) {
            try {
                SimpleMessage simpleMessage = (SimpleMessage) ObjectSerializationUtils
                        .deserialize(ObjectSerializationUtils.getBytes(req,
                                logger));
                //
                PagingResult<Device> devicePagingResult = deviceDao
                        .getPageOfAll(PAGE_SIZE, simpleMessage.getCursor());
                //
                if (!devicePagingResult.getResultList().isEmpty()) {
                    // Create multicast with 1000 devices
                    String multicastKey = multicastService
                            .createMulticast(devicePagingResult.getResultList());
                    MulticastMessage mm = new MulticastMessage(multicastKey,
                            simpleMessage);
                    // Send message to gcm
                    taskLauncher.launchTask(TaskLauncher.QUEUE_GCM,
                            SendMessageTask.class,
                            ObjectSerializationUtils.serialize(mm));
                    // Iterate through over next 1000 elements
                    if (devicePagingResult.getCursor() != null) {
                        simpleMessage.setCursor(devicePagingResult.getCursor());
                        taskLauncher.launchTask(TaskLauncher.QUEUE_GCM_PAGED,
                                SendAllMessageTask.class,
                                ObjectSerializationUtils
                                        .serialize(simpleMessage));
                    }
                }
                //
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Unable to send messages", e);
            }
        }
    }

    @Inject
    public void setMulticastService(MulticastService multicastService) {
        this.multicastService = multicastService;
    }

    @Inject
    public void setTaskLauncher(TaskLauncher taskLauncher) {
        this.taskLauncher = taskLauncher;
    }

    @Inject
    public void setDeviceDao(DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }
}
