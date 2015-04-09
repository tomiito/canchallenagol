package ar.com.ironsoft.marroccl.web.app.modules.devices.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.com.ironsoft.marroccl.web.app.modules.devices.daos.DeviceDao;
import ar.com.ironsoft.marroccl.web.app.modules.devices.model.Device;
import ar.com.ironsoft.marroccl.web.core.servlets.BaseServlet;
import ar.com.ironsoft.marroccl.web.guice.base.BasePath;
import ar.com.ironsoft.marroccl.web.guice.base.RelativePath;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Tomas de Priede
 */
@Singleton
@BasePath("/devices/")
@RelativePath("unregister")
public class DeviceUnregistrationServlet extends BaseServlet {

    private DeviceDao deviceDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String deviceId = String.valueOf(req.getParameter("deviceId"));
        Preconditions.checkNotNull(deviceId);
        //
        deviceDao.delete(Device.class, deviceId);
        //
        setSuccess(resp);
    }

    @Inject
    public void setDeviceDao(DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }
}
