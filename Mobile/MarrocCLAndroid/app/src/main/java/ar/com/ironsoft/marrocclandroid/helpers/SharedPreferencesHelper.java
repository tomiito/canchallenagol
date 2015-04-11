package ar.com.ironsoft.marrocclandroid.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mcapolupo on 15/09/2014.
 */
public class SharedPreferencesHelper {

    public static final String EVENTS_MATCH = "event_match";

    private static SharedPreferences getPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getString(Context context, String key, String defaultValue) {
        return getPreference(context).getString(key, defaultValue);
    }

    public static String getString(Context context, String key) {
        return getString(context, key, "null");
    }

    public static int getInt(Context context, String key, int defaultValue) {
        return getPreference(context).getInt(key, defaultValue);
    }

    public static int getInt(Context context, String key) {
        return getInt(context, key, 0);
    }

    public static void setString(Context context, String key, String value) {
        getPreference(context).edit().putString(key, value).commit();
    }

    public static void setInt(Context context, String key, int value) {
        getPreference(context).edit().putInt(key, value).commit();

    }

    public static void removeProperty(Context context, String user_contact_data) {
        getPreference(context).edit().remove(user_contact_data).commit();

    }

    public static void setSetOfStrings(Context context, String key, Set<String> recordsPending){
        getPreference(context).edit().putStringSet(key, recordsPending).commit();
    }

    public static Set<String> getSetOfStrings(Context context, String key){
        return getPreference(context).getStringSet(key, new HashSet<String>());
    }


}
