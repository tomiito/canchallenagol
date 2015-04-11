package ar.com.ironsoft.marrocclandroid.intentServices;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Set;

import ar.com.ironsoft.marrocclandroid.R;
import ar.com.ironsoft.marrocclandroid.activities.EventActivity;
import ar.com.ironsoft.marrocclandroid.domain.PushMessage;
import ar.com.ironsoft.marrocclandroid.helpers.SharedPreferencesHelper;
import ar.com.ironsoft.marrocclandroid.receivers.GcmBroadcastReceiver;

/**
 * Created by gabrielvilloldo on 4/8/15.
 */
public class GcmIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;

    public GcmIntentService() {
        super("GcmIntentService");
    }
    public static final String TAG = "MarrocCLTag";

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            String message = extras.getString("message");

            switch(messageType) {
                case GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR:
                    // Do Nothing
                    break;
                case GoogleCloudMessaging.MESSAGE_TYPE_DELETED:
                    // Do Nothing
                    break;
                case GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE:
                    PushMessage pushMessage = parsePushMessage(extras);

                    sendNotification(pushMessage);
                    break;
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(PushMessage pushMessage) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, EventActivity.class);
        intent.putExtra("pushMessage", pushMessage);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle(pushMessage.getTitle())
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(pushMessage.getTitle()))
                        .setAutoCancel(true)
                        .setContentText(pushMessage.getMessage());

        switch (pushMessage.getType()) {
            case "goal":
                mBuilder.setSmallIcon(R.drawable.ic_ball);
                break;
            case "yellow card":
                mBuilder.setSmallIcon(R.drawable.yellow_card);
                break;
            case "red card":
                mBuilder.setSmallIcon(R.drawable.yellow_card);
                break;
            default:
                mBuilder.setSmallIcon(R.drawable.shoes);
                break;
        }
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    private PushMessage parsePushMessage(Bundle bundle) {
        PushMessage pushMessage = new PushMessage();
        pushMessage.setGameId(bundle.getString("gameId"));
        try {
            pushMessage.setTitle(URLDecoder.decode(bundle.getString("title"), "UTF-8"));
            pushMessage.setMessage(URLDecoder.decode(bundle.getString("message"), "UTF-8"));
            pushMessage.setType(bundle.getString("type"));
            pushMessage.setPlayer(URLDecoder.decode(bundle.getString("player"), "UTF-8"));
            pushMessage.setPlayer2(URLDecoder.decode(bundle.getString("player2"), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //pushMessage.setGifLink(bundle.getString("gifLink"));
        pushMessage.setVideoLink(bundle.getString("videoLink"));
        //pushMessage.setThumbnailLink(bundle.getString("thumbnailLink"));
        pushMessage.setMinutes(Integer.parseInt(bundle.getString("minutes")));
        pushMessage.setSeconds(Integer.parseInt(bundle.getString("seconds")));
        return pushMessage;
    }
}