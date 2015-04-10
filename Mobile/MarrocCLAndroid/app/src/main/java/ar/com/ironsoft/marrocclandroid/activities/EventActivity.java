package ar.com.ironsoft.marrocclandroid.activities;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import ar.com.ironsoft.marrocclandroid.R;
import ar.com.ironsoft.marrocclandroid.domain.PushMessage;


public class EventActivity extends ActionBarActivity {

    Context context;
    PushMessage pushMessage;

    private VideoView videoView;
    private ProgressBar progressBar;
    private ImageView thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        setUI();
        processPushMessageFromIntent();

        playVideo();
    }

    private void processPushMessageFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            pushMessage = intent.getParcelableExtra("pushMessage");
        }
    }

    private void setUI() {
        videoView = (VideoView)findViewById(R.id.video);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);

    }

    private void playVideo() {
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(Uri.parse(pushMessage.getVideoLink()));
        //videoView.setVideoURI(Uri.parse("https://s3.amazonaws.com/historico.lanacion.com.ar/Partidos/TYC.20150331_225631.mp4"));
        videoView.requestFocus();
        progressBar.setVisibility(View.VISIBLE);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int arg1, int arg2) {
                        progressBar.setVisibility(View.GONE);
                        mp.start();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
