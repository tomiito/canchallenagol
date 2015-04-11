package ar.com.ironsoft.marroccl.web.app.modules.game.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.com.ironsoft.marroccl.web.app.modules.game.services.GameService;
import ar.com.ironsoft.marroccl.web.core.servlets.BaseServlet;
import ar.com.ironsoft.marroccl.web.guice.base.BasePath;
import ar.com.ironsoft.marroccl.web.guice.base.RelativePath;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Parse xml after 1 minutes
 *
 * @author Tomas de Priede
 */
@Singleton
@BasePath("/game/")
@RelativePath("parseGame")
public class ParseGameServlet extends BaseServlet {

    private Logger logger = Logger.getLogger(ParseGameServlet.class
            .getSimpleName());
    private GameService gameService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        gameService.parseGame();
        setSuccess(resp);
    }

    @Inject
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }
}
