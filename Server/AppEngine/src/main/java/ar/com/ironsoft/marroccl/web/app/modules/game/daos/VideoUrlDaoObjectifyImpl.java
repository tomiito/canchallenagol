package ar.com.ironsoft.marroccl.web.app.modules.game.daos;

import ar.com.ironsoft.marroccl.web.app.modules.game.model.VideoUrl;
import ar.com.ironsoft.marroccl.web.core.daos.BaseDaoObjectifyImpl;
import ar.com.ironsoft.marroccl.web.core.model.paging.PagingResult;

import com.google.inject.Singleton;

/**
 * @author Tomas de Priede
 */
@Singleton
public class VideoUrlDaoObjectifyImpl extends BaseDaoObjectifyImpl<VideoUrl>
        implements VideoUrlDao {

    @Override
    public void deleteAll() {
        PagingResult<VideoUrl> pagingResult = getPageOfAll(VideoUrl.class,
                1000, null);
        deleteEntities(pagingResult.getResultList());
    }
}
