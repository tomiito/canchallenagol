package ar.com.ironsoft.marroccl.web.app.modules.game.daos;

import ar.com.ironsoft.marroccl.web.app.modules.game.model.Game;
import ar.com.ironsoft.marroccl.web.core.daos.BaseDao;

import com.google.inject.ImplementedBy;

/**
 * @author Tomas de Priede
 */
@ImplementedBy(GameDaoObjectifyImpl.class)
public interface GameDao extends BaseDao<Game> {
}
