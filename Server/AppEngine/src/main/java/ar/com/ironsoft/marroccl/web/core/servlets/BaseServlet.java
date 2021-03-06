package ar.com.ironsoft.marroccl.web.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;

/**
 * Skeleton class for all servlets in this package.
 */
@SuppressWarnings("serial")
public abstract class BaseServlet extends HttpServlet {

    // change to true to allow GET calls
    static final boolean DEBUG = true;
    protected final Logger logger = Logger.getLogger(getClass().getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        if (DEBUG) {
            doPost(req, resp);
        } else {
            super.doGet(req, resp);
        }
    }

    protected void setJsonResponse(HttpServletResponse response, String json)
            throws IOException {
        response.setContentType("application/json");
        // Get the printwriter object from response to write the required json
        // object to the output stream
        PrintWriter out = response.getWriter();
        // Assuming your json object is **jsonObject**, perform the following,
        // it will return your json object
        out.print(json);
        out.flush();
    }

    protected String getParameter(HttpServletRequest req, String parameter)
            throws ServletException {
        String value = req.getParameter(parameter);
        if (Strings.isNullOrEmpty(value)) {
            if (DEBUG) {
                StringBuilder parameters = new StringBuilder();
                @SuppressWarnings("unchecked")
                Enumeration<String> names = req.getParameterNames();
                while (names.hasMoreElements()) {
                    String name = names.nextElement();
                    String param = req.getParameter(name);
                    parameters.append(name).append("=").append(param)
                            .append("\n");
                }
                logger.fine("parameters: " + parameters);
            }
            throw new ServletException("Parameter " + parameter + " not found");
        }
        return value.trim();
    }

    protected String getParameter(HttpServletRequest req, String parameter,
            String defaultValue) {
        String value = req.getParameter(parameter);
        if (Strings.isNullOrEmpty(value)) {
            value = defaultValue;
        }
        return value.trim();
    }

    protected void setSuccess(HttpServletResponse resp) {
        setSuccess(resp, 0);
    }

    protected void setSuccess(HttpServletResponse resp, int size) {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/plain");
        resp.setContentLength(size);
    }
}