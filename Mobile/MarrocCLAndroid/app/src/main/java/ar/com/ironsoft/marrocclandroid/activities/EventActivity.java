package ar.com.ironsoft.marrocclandroid.activities;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import ar.com.ironsoft.marrocclandroid.R;
import ar.com.ironsoft.marrocclandroid.domain.PushMessage;


public class EventActivity extends BaseActionBarActivity {

    Context context;
    PushMessage pushMessage;

    private VideoView videoView;
    private ProgressBar progressBar;
    private ImageView thumbnail;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("message", pushMessage);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        context = this;

        if (!processPushMessageFromIntent()) {
            pushMessage = savedInstanceState.getParcelable("publication");
        }
        setUI();
        playVideo();
    }

    private Boolean processPushMessageFromIntent() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras().size() > 0) {
            pushMessage = intent.getParcelableExtra("pushMessage");
            return true;
        }
        return false;
    }

    private void setUI() {
        videoView = (VideoView)findViewById(R.id.video);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        thumbnail = (ImageView)findViewById(R.id.thumbnail);

        ((TextView)findViewById(R.id.event_title)).setText(pushMessage.getTitle());
        ((TextView)findViewById(R.id.event_message)).setText(pushMessage.getMessage());
        //((TextView)findViewById(R.id.event_time)).setText(pushMessage.getMinutes());
    }

    private void playVideo() {
        if (pushMessage.getVideoLink() == null) {
            thumbnail.setVisibility(View.GONE);
            return;
        }
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(Uri.parse(pushMessage.getVideoLink()));
        //ImageLoader.getInstance().displayImage(pushMessage.getThumbnailLink(), thumbnail); // TODO: GV Add cache in Memory.
        //videoView.setVideoURI(Uri.parse("https://s3.amazonaws.com/historico.lanacion.com.ar/Partidos/TYC.20150331_225631.mp4"));
        videoView.requestFocus();
        progressBar.setVisibility(View.VISIBLE);
        thumbnail.setVisibility(View.VISIBLE);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int arg1, int arg2) {
                        progressBar.setVisibility(View.GONE);
                        thumbnail.setVisibility(View.GONE);
                        mp.start();
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
