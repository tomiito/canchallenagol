package ar.com.ironsoft.marroccl.web.app.modules.messages.tasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.com.ironsoft.marroccl.web.app.modules.devices.daos.DeviceDao;
import ar.com.ironsoft.marroccl.web.app.modules.devices.model.Device;
import ar.com.ironsoft.marroccl.web.app.modules.messages.daos.MulticastDao;
import ar.com.ironsoft.marroccl.web.app.modules.messages.model.Multicast;
import ar.com.ironsoft.marroccl.web.app.modules.messages.model.MulticastMessage;
import ar.com.ironsoft.marroccl.web.app.modules.messages.services.MulticastService;
import ar.com.ironsoft.marroccl.web.core.constants.SharedConstants;
import ar.com.ironsoft.marroccl.web.core.tasks.TaskServlet;
import ar.com.ironsoft.marroccl.web.core.utils.ObjectSerializationUtils;
import ar.com.ironsoft.marroccl.web.gcm.GCMConstants;
import ar.com.ironsoft.marroccl.web.gcm.GCMMessage;
import ar.com.ironsoft.marroccl.web.gcm.MulticastResult;
import ar.com.ironsoft.marroccl.web.gcm.Result;
import ar.com.ironsoft.marroccl.web.gcm.Sender;
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
@RelativePath("send")
public class SendMessageTask extends TaskServlet {

    private Logger logger = Logger.getLogger(SendMessageTask.class
            .getSimpleName());
    private MulticastDao multicastDao;
    private Sender sender;
    private DeviceDao deviceDao;
    private MulticastService multicastService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sender = new Sender(SharedConstants.API_KEY);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.log(Level.INFO,
                "Starting task" + SendMessageTask.class.getSimpleName());
        if (doInitialTaskCheck(req, resp)) {
            logger.log(Level.INFO, "Pass initial checking");
            try {
                MulticastMessage mm = (MulticastMessage) ObjectSerializationUtils
                        .deserialize(ObjectSerializationUtils.getBytes(req,
                                logger));
                Multicast multicast = multicastDao.get(Multicast.class,
                        mm.getMulticastKey());
                List<String> deviceIds = multicast.getDeviceIds();
                GCMMessage gcm = mm.getMessage().asGCM();
                //
                MulticastResult multicastResult;
                boolean allDone = true;
                try {
                    logger.log(Level.INFO, "Sending multitask");
                    multicastResult = sender.sendNoRetry(gcm, deviceIds);
                    logger.log(Level.INFO, "Processing result");
                    allDone = processMulticastResult(mm, deviceIds,
                            multicastResult);
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Exception posting " + gcm, e);
                }
                //
                logger.log(Level.INFO, "Multicast done");
                multicastDone(resp, mm.getMulticastKey());
                //
                if (allDone) {
                    multicastDone(resp, mm.getMulticastKey());
                } else {
                    retryTask(resp);
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Unable to send message to gcm", e);
            }
        }
    }

    private boolean processMulticastResult(MulticastMessage mm,
            List<String> deviceIds, MulticastResult multicastResult) {
        boolean allDone = true;
        List<Result> results = multicastResult.getResults();
        //
        // check if any registration id must be updated
        if (multicastResult.getCanonicalIds() != 0) {
            for (int i = 0; i < results.size(); i++) {
                String canonicalRegId = results.get(i)
                        .getCanonicalRegistrationId();
                if (canonicalRegId != null) {
                    String regId = deviceIds.get(i);
                    deviceDao.delete(Device.class, regId);
                    deviceDao.save(new Device(canonicalRegId));
                }
            }
        }
        //
        if (multicastResult.getFailure() != 0) {
            // there were failures, check if any could be retried
            List<String> retriableRegIds = new ArrayList<String>();
            for (int i = 0; i < results.size(); i++) {
                String error = results.get(i).getErrorCodeName();
                if (error != null) {
                    String regId = deviceIds.get(i);
                    logger.warning("Got error (" + error + ") for regId "
                            + regId);
                    if (error.equals(GCMConstants.ERROR_NOT_REGISTERED)
                            || error.equals(GCMConstants.ERROR_INVALID_REGISTRATION)) {
                        // application has been removed from device - unregister
                        // it
                        deviceDao.delete(Device.class, regId);
                    }
                    if (error.equals(GCMConstants.ERROR_UNAVAILABLE)) {
                        retriableRegIds.add(regId);
                    }
                }
            }
            if (!retriableRegIds.isEmpty()) {
                // update task
                multicastService.updateMulticast(mm.getMulticastKey(),
                        retriableRegIds);
                allDone = false;
            }
        }
        return allDone;
    }

    private void multicastDone(HttpServletResponse resp, String multicastId) {
        multicastDao.delete(Multicast.class, multicastId);
        taskDone(resp);
    }

    @Inject
    public void setDeviceDao(DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }

    @Inject
    public void setMulticastService(MulticastService multicastService) {
        this.multicastService = multicastService;
    }

    @Inject
    public void setMulticastDao(MulticastDao multicastDao) {
        this.multicastDao = multicastDao;
    }
}
