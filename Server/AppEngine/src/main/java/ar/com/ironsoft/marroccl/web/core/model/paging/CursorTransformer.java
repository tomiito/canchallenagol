package ar.com.ironsoft.marroccl.web.core.model.paging;

import com.google.api.server.spi.config.Transformer;
import com.google.gson.Gson;

/**
 * @author Tomas de Priede
 */
public class CursorTransformer implements Transformer<Cursor, String> {

    @Override
    public String transformTo(Cursor cursor) {
        Gson gson = new Gson();
        return gson.toJson(cursor);
    }

    @Override
    public Cursor transformFrom(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Cursor.class);
    }
}
