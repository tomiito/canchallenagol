package ar.com.ironsoft.marroccl.web.app.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.com.ironsoft.marroccl.web.core.constants.SharedConstants;
import ar.com.ironsoft.marroccl.web.core.servlets.BaseServlet;
import ar.com.ironsoft.marroccl.web.guice.base.BasePath;
import ar.com.ironsoft.marroccl.web.guice.base.RelativePath;

import com.google.inject.Singleton;

@Singleton
@BasePath("/")
@RelativePath("")
public class IndexServlet extends BaseServlet {

    static final String ATTRIBUTE_STATUS = "status";

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher(SharedConstants.INDEX_JSP_URL);
        dispatcher.forward(request, response);
    }
    /**
     * Displays the existing messages and offer the option to send a new one.
     */
    // @Override
    // protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    // throws IOException {
    // resp.setContentType("text/html");
    // PrintWriter out = resp.getWriter();
    // out.print("<html><body>");
    // out.print("<head>");
    // out.print("  <title>GCM Demo</title>");
    // out.print("  <link rel='icon' href='favicon.png'/>");
    // out.print("</head>");
    // String status = (String) req.getAttribute(ATTRIBUTE_STATUS);
    // if (status != null) {
    // out.print(status);
    // }
    // int total = Datastore.getTotalDevices();
    // if (total == 0) {
    // out.print("<h2>No devices registered!</h2>");
    // } else {
    // out.print("<h2>" + total + " device(s) registered!</h2>");
    // out.print("<form name='form' method='POST' action='/sendAll/'>");
    // out.print("<input type='submit' value='Send Message' />");
    // out.print("</form>");
    // }
    // out.print("</body></html>");
    // resp.setStatus(HttpServletResponse.SC_OK);
    // }
}
