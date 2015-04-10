package ar.com.ironsoft.marrocclandroid.intentServices;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import ar.com.ironsoft.marrocclandroid.R;
import ar.com.ironsoft.marrocclandroid.activities.MainActivity;
import ar.com.ironsoft.marrocclandroid.domain.PushMessage;
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


            switch(messageType) {
                case GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR:
                    // Do Nothing
                    break;
                case GoogleCloudMessaging.MESSAGE_TYPE_DELETED:
                    // Do Nothing
                    break;
                case GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE:
                    sendNotification(new PushMessage("", extras.toString()));
                    break;
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(PushMessage pushMessage) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_ball)
                        .setContentTitle(pushMessage.getTitle())
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(pushMessage.getTitle()))
                        .setContentText(pushMessage.getMessage());

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}