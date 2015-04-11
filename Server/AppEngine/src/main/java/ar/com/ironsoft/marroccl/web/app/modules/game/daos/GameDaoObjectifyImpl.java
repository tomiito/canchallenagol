package ar.com.ironsoft.marroccl.web.app.modules.game.daos;

import ar.com.ironsoft.marroccl.web.app.modules.game.model.Game;
import ar.com.ironsoft.marroccl.web.core.daos.BaseDaoObjectifyImpl;

import com.google.inject.Singleton;

/**
 * @author Tomas de Priede
 */
@Singleton
public class GameDaoObjectifyImpl extends BaseDaoObjectifyImpl<Game> implements
        GameDao {
}
