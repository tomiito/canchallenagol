package ar.com.ironsoft.marroccl.web.app.modules.game.daos;

import static ar.com.ironsoft.marroccl.web.core.daos.OfyService.ofy;

import java.util.List;

import ar.com.ironsoft.marroccl.web.app.modules.game.xml.model.Message;
import ar.com.ironsoft.marroccl.web.core.daos.BaseDaoObjectifyImpl;

import com.google.inject.Singleton;
import com.googlecode.objectify.cmd.Query;

/**
 * @author Tomas de Priede
 */
@Singleton
public class MessageDaoObjectifyImpl extends BaseDaoObjectifyImpl<Message>
        implements MessageDao {

    @Override
    public List<Message> getAll(int minute, int second) {
        int minuteSecond = minute * 100 + second;
        Query<Message> query = ofy().load().type(Message.class)
                .filter("minuteSecond <=", minuteSecond).order("-minuteSecond")
                .limit(1000);
        return query.list();
    }
}
