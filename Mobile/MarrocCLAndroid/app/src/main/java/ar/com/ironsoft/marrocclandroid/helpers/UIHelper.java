package ar.com.ironsoft.marrocclandroid.helpers;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by gabrielvilloldo on 4/8/15.
 */
public class UIHelper {

    public static boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }
}