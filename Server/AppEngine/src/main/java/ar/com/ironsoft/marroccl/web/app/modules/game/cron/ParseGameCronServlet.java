package ar.com.ironsoft.marroccl.web.app.modules.game.cron;

import ar.com.ironsoft.marroccl.web.core.servlets.BaseServlet;
import ar.com.ironsoft.marroccl.web.guice.base.BasePath;
import ar.com.ironsoft.marroccl.web.guice.base.RelativePath;

import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Parse xml after 1 minutes
 *
 * @author Tomas de Priede
 */
@Singleton
@BasePath("/cron/")
@RelativePath("parseGame")
public class ParseGameCronServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
          //Receive game
    }
}
