package ar.com.ironsoft.marrocclandroid.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by gabrielvilloldo on 4/8/15.
 */
public class ConnectionHelper {

    public static Boolean checkConnection(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info == null)
            return false;
        if (!info.isConnected())
            return false;
        return info.isAvailable();
    }

    /**
     * Check if there is any connectivity
     * @param context
     * @return
     */
    public static boolean isConnected(Context context){
        NetworkInfo info = ConnectionHelper.getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    /**
     * Check if there is any connectivity and if the host is reachable
     * @param context
     * @return
     */
    /*public static void isConnectedAndReachable(Context context, final String host, final ServiceCallback<Boolean> callback){
        final NetworkInfo info = ConnectionHelper.getNetworkInfo(context);
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    InetAddress address = InetAddress.getByName(host);
                    callback.execute(address != null && info != null && info.isConnected());
                } catch (UnknownHostException e) {
                    LoggerHelper.e(e, ConnectionHelper.class.getName());
                }
                return null;
            }
        }.execute();
    }*/

    /**
     * Check if there is any connectivity to a Wifi network
     * @param context
     * @return
     */
    public static boolean isConnectedWifi(Context context){
        NetworkInfo info = ConnectionHelper.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    /**
     * Check if there is any connectivity to a mobile network
     * @param context
     * @return
     */
    public static boolean isConnectedMobile(Context context) {
        NetworkInfo info = ConnectionHelper.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    /**
     * Get the network info
     * @param context
     * @return
     */
    private static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm == null ? null : cm.getActiveNetworkInfo();
    }
}