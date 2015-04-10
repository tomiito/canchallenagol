package ar.com.ironsoft.marroccl.web.app.modules.game.daos;

import ar.com.ironsoft.marroccl.web.app.modules.game.xml.model.Commentary;
import ar.com.ironsoft.marroccl.web.core.daos.BaseDao;

import com.google.inject.ImplementedBy;

/**
 * @author Tomas de Priede
 */
@ImplementedBy(CommentaryDaoObjectifyImpl.class)
public interface CommentaryDao extends BaseDao<Commentary> {
}
