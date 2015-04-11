package ar.com.ironsoft.marroccl.web.app.modules.game.daos;

import ar.com.ironsoft.marroccl.web.app.modules.game.model.GameGoal;
import ar.com.ironsoft.marroccl.web.core.daos.BaseDao;

import com.google.inject.ImplementedBy;

/**
 * @author Tomas de Priede
 */
@ImplementedBy(GameGoalDaoObjectifyImpl.class)
public interface GameGoalDao extends BaseDao<GameGoal> {

    void deleteAll();

    GameGoal findByMinuteAndSecond(Integer minute, Integer second);
}
