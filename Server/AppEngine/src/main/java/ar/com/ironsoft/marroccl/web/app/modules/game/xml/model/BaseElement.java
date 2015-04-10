package ar.com.ironsoft.marroccl.web.app.modules.game.xml.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Tomas de Priede
 */
public abstract class BaseElement {

    /**
     * Format game_date="2015-04-01 01:00:00"
     * 
     * @param stringDate
     * @return
     */
    protected Date parseDate(String stringDate) {
        Date date = null;
        if (stringDate != null) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                date = dateFormat.parse(stringDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    protected Integer parseInteger(String value) {
        Integer result = 0;
        try {
            if (value != null) {
                result = Integer.parseInt(value);
            }
        } catch (NumberFormatException e) {
            // do nothing
        }
        return result;
    }
}
