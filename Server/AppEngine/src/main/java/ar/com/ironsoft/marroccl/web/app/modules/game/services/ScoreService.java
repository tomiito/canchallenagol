package ar.com.ironsoft.marroccl.web.app.modules.game.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import ar.com.ironsoft.marroccl.web.app.modules.game.daos.GameGoalDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.model.GameGoal;
import ar.com.ironsoft.marroccl.web.app.modules.game.xml.model.Commentary;
import ar.com.ironsoft.marroccl.web.app.modules.game.xml.model.Message;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Tomas de Priede
 */
@Singleton
public class ScoreService {

    private GameGoalDao gameGoalDao;

    public void processGoals(Commentary commentary) {
        List<GameGoal> goals = new ArrayList<>();
        if (commentary != null) {
            for (Message message : commentary.getMessageList()) {
                if ("goal".equals(message.getType())) {
                    String goalMsg = StringUtils.split(message.getComment(),
                            "(")[0];
                    String[] split = StringUtils.split(goalMsg, " ");
                    int homeGoals = Integer.parseInt(StringUtils.remove(
                            split[2], ","));
                    int awayGoals = Integer.parseInt(StringUtils.remove(
                            split[4], "."));
                    //
                    GameGoal gameGoal = new GameGoal(commentary.getGameId());
                    //
                    gameGoal.setAwayTeamScore(homeGoals);
                    gameGoal.setHomeTeamScore(awayGoals);
                    gameGoal.setMinute(message.getMinute());
                    gameGoal.setSecond(message.getSecond());
                    gameGoal.setTime(message.getMinute() * 100
                            + message.getSecond());
                    gameGoal.setScorer(StringUtils.split(goalMsg, ".")[1]);
                    //
                    goals.add(gameGoal);
                }
            }
        }
        gameGoalDao.saveAll(goals);
    }

    @Inject
    public void setGameGoalDao(GameGoalDao gameGoalDao) {
        this.gameGoalDao = gameGoalDao;
    }
}
