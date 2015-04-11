package ar.com.ironsoft.marroccl.web.app.modules.game.daos;

import ar.com.ironsoft.marroccl.web.app.modules.game.model.VideoUrl;
import ar.com.ironsoft.marroccl.web.core.daos.BaseDao;

import com.google.inject.ImplementedBy;

/**
 * @author Tomas de Priede
 */
@ImplementedBy(VideoUrlDaoObjectifyImpl.class)
public interface VideoUrlDao extends BaseDao<VideoUrl> {

    void deleteAll();

    VideoUrl findByMinute(Integer minute);
}
