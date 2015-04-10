package ar.com.ironsoft.marroccl.web.app.modules.game.daos;

import ar.com.ironsoft.marroccl.web.app.modules.game.xml.model.Commentary;
import ar.com.ironsoft.marroccl.web.core.daos.BaseDaoObjectifyImpl;

import com.google.inject.Singleton;

/**
 * @author Tomas de Priede
 */
@Singleton
public class CommentaryDaoObjectifyImpl extends
        BaseDaoObjectifyImpl<Commentary> implements CommentaryDao {
}
