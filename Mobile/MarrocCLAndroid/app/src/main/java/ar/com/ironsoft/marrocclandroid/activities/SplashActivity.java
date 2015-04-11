package ar.com.ironsoft.marrocclandroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import ar.com.ironsoft.marrocclandroid.R;


public class SplashActivity extends Activity {

    private static final long SPLASH_SCREEN_DELAY = 1000;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                Intent mainIntent = new Intent().setClass(
                        SplashActivity.this, GameSelectActivity.class);
                startActivity(mainIntent);

                finish();
            }
        };

        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer.purge();
    }

}
