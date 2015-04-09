package ar.com.ironsoft.marroccl.web.app.modules.messages.tasks;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.com.ironsoft.marroccl.web.app.modules.messages.daos.MulticastDao;
import ar.com.ironsoft.marroccl.web.app.modules.messages.model.Multicast;
import ar.com.ironsoft.marroccl.web.app.modules.messages.model.MulticastMessage;
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
@RelativePath("send")
public class SendMessageTask extends TaskServlet {

    private Logger logger = Logger.getLogger(SendMessageTask.class
            .getSimpleName());
    private MulticastDao multicastDao;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (doInitialTaskCheck(req, resp)) {
            try {
                MulticastMessage mm = (MulticastMessage) ObjectSerializationUtils
                        .deserialize(ObjectSerializationUtils.getBytes(req,
                                logger));
                Multicast multicast = multicastDao.get(Multicast.class,
                        mm.getMulticastKey());
                Set<String> devicesId = multicast.getDeviceIds();
                //
                //
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Unable to send message to gcm", e);
            }
        }
    }

    @Inject
    public void setMulticastDao(MulticastDao multicastDao) {
        this.multicastDao = multicastDao;
    }
}
