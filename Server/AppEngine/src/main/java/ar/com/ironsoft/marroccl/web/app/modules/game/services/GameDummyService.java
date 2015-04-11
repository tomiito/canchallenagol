package ar.com.ironsoft.marroccl.web.app.modules.game.services;

import java.util.ArrayList;
import java.util.List;

import ar.com.ironsoft.marroccl.web.app.modules.game.daos.GameDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.model.Game;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Team ids <br />
 * River Plate 1 <br />
 * Boca Juniors 2 <br />
 * Independiente 3 <br />
 * Racing 4<br />
 * San Lorenzo 5<br />
 * Gimnasia LP 6<br />
 * Huracan 7<br />
 * Estudiantes LP 8<br />
 * Lanus 9 <br />
 * Argentinos 10<br />
 * <br />
 * 
 * @author Tomas de Priede
 */
@Singleton
public class GameDummyService {

    private GameDao gameDao;

    public void createDummyGames() {
        List<Game> games = new ArrayList<>();
        games.add(createGame1());
        games.add(createGame2());
        games.add(createGame3());
        games.add(createGame4());
        games.add(createGame5());
        //
        gameDao.saveAll(games);
    }

    private Game createGame1() {
        Game game = new Game();
        game.setGameId("1");
        game.setTime("90:00");
        game.setStatus(3);
        //
        game.setHomeTeamId("7");
        game.setHomeTeamName("Huracan");
        game.setHomeTeamLink("http://lh4.ggpht.com/-RSYEbp16AGg/T_S1vj_lekI/AAAAAAAABy8/YeHi38pHfBY/s150/huracan.png");
        game.setHomeTeamScore(0);
        //
        game.setAwayTeamId("2");
        game.setAwayTeamName("Boca Juniors");
        game.setAwayTeamLink("http://lh3.ggpht.com/-F2Zh5rBDCQs/T_S3u8W3mHI/AAAAAAAADIM/rmvMETxY3C4/s150/bocajuniors.png");
        game.setAwayTeamScore(2);
        //
        return game;
    }

    private Game createGame2() {
        Game game = new Game();
        game.setGameId("2");
        game.setTime("00:00");
        game.setStatus(1);
        //
        game.setHomeTeamId("8");
        game.setHomeTeamName("Estudiantes LP");
        game.setHomeTeamLink("http://lh4.googleusercontent.com/-PyvFFHG0LK8/UnR9RJ6T1kI/AAAAAAAADVg/rolZ5K5TPKk/s150/estudianteslp.png");
        game.setHomeTeamScore(0);
        //
        game.setAwayTeamId("4");
        game.setAwayTeamName("Racing");
        game.setAwayTeamLink("http://lh3.ggpht.com/-YD8xS5JI-r8/T_S4EBiutFI/AAAAAAAADRQ/cB2d8PYTzko/s150/racingclub.png");
        game.setAwayTeamScore(0);
        //
        return game;
    }

    private Game createGame3() {
        Game game = new Game();
        game.setGameId("3");
        game.setTime("00:00");
        game.setStatus(1);
        //
        game.setHomeTeamId("9");
        game.setHomeTeamName("Lanus");
        game.setHomeTeamLink("http://lh5.ggpht.com/-L9z7BWtrf4o/T_S3_bTDETI/AAAAAAAADPQ/xrnz51ot3g4/s150/lanus.png");
        game.setHomeTeamScore(0);
        //
        game.setAwayTeamId("10");
        game.setAwayTeamName("Argentinos");
        game.setAwayTeamLink("http://lh3.ggpht.com/-FRNjsLp2asc/T_S3siSQHsI/AAAAAAAADHI/1p0DdN_qi9c/s150/argentinos.png");
        game.setAwayTeamScore(0);
        //
        return game;
    }

    private Game createGame4() {
        Game game = new Game();
        game.setGameId("4");
        game.setTime("15:43");
        game.setStatus(2);
        //
        game.setHomeTeamId("3");
        game.setHomeTeamName("Independiente");
        game.setHomeTeamLink("http://lh6.ggpht.com/-a2AaUl2K1pk/UAmoC0dBjHI/AAAAAAAADXA/Pbmbv3_XsSk/s150/independiente.png");
        game.setHomeTeamScore(0);
        //
        game.setAwayTeamId("6");
        game.setAwayTeamName("Gimnasia LP");
        game.setAwayTeamLink("http://lh5.ggpht.com/-Be3IP2CxEYA/T_Ua2BGXK6I/AAAAAAAAB8A/80rxnomgX9s/s150/gimnasialp.png");
        game.setAwayTeamScore(0);
        //
        return game;
    }

    private Game createGame5() {
        Game game = new Game();
        game.setGameId("5");
        game.setTime("90:00");
        game.setStatus(3);
        //
        game.setHomeTeamId("1");
        game.setHomeTeamName("River Plate");
        game.setHomeTeamLink("http://lh4.ggpht.com/-M4TSXz-rRKg/T_S4FbkWK-I/AAAAAAAADR4/Oq2Ed2h2lN0/s150/riverplate.png");
        game.setHomeTeamScore(1);
        //
        game.setAwayTeamId("5");
        game.setAwayTeamName("San Lorenzo");
        game.setAwayTeamLink("http://lh6.ggpht.com/-ALlV82LjBew/T_S4GJPJJPI/AAAAAAAADSM/UIfqIIqNxnw/s150/sanlorenzo.png");
        game.setAwayTeamScore(0);
        //
        return game;
    }

    @Inject
    public void setGameDao(GameDao gameDao) {
        this.gameDao = gameDao;
    }
}
