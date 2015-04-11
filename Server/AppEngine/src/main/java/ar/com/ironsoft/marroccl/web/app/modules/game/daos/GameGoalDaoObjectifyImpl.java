package ar.com.ironsoft.marroccl.web.app.modules.game.daos;

import static ar.com.ironsoft.marroccl.web.core.daos.OfyService.ofy;

import ar.com.ironsoft.marroccl.web.app.modules.game.model.GameGoal;
import ar.com.ironsoft.marroccl.web.core.daos.BaseDaoObjectifyImpl;
import ar.com.ironsoft.marroccl.web.core.model.paging.PagingResult;

import com.google.inject.Singleton;
import com.googlecode.objectify.cmd.Query;

/**
 * @author Tomas de Priede
 */
@Singleton
public class GameGoalDaoObjectifyImpl extends BaseDaoObjectifyImpl<GameGoal>
        implements GameGoalDao {

    @Override
    public void deleteAll() {
        PagingResult<GameGoal> goalPagingResult = getPageOfAll(GameGoal.class,
                100, null);
        deleteEntities(goalPagingResult.getResultList());
    }

    @Override
    public GameGoal findByMinuteAndSecond(Integer minute, Integer second) {
        Integer time = minute * 100 + second;
        Query<GameGoal> query = ofy().load().type(GameGoal.class)
                .filter("time <=", time).order("-time").limit(1);
        return query.first().now();
    }
}
