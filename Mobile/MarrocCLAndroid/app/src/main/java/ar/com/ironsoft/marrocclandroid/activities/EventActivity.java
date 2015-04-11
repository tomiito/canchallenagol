package ar.com.ironsoft.marrocclandroid.activities;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

import ar.com.ironsoft.marrocclandroid.R;
import ar.com.ironsoft.marrocclandroid.adapters.GameListAdapter;
import ar.com.ironsoft.marrocclandroid.domain.GameItem;
import ar.com.ironsoft.marrocclandroid.domain.PushMessage;
import ar.com.ironsoft.marrocclandroid.helpers.EventTypeHelper;


public class EventActivity extends BaseActionBarActivity {

    Context context;
    PushMessage pushMessage;

    private VideoView videoView;
    private ProgressBar progressBar;
    private ImageView thumbnail;
    private ShareActionProvider shareActionProvider;

    private View scoreBoardContainer;
    private View loadingPartial;


    private EventTypeHelper eventTypeHelper;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("message", pushMessage);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        eventTypeHelper = new EventTypeHelper();

        context = this;
        scoreBoardContainer = findViewById(R.id.score_board_container);
        loadingPartial = findViewById(R.id.loading_circular_partial_container);

        if (!processPushMessageFromIntent()) {
            pushMessage = savedInstanceState.getParcelable("message");
        }
        setShareIntent();
        setUI();
        playVideo();
        getScoreBoard();
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

        ImageView bigIcon = (ImageView)findViewById(R.id.list_item_main_event_image);
        ImageView playerIcon = (ImageView)findViewById(R.id.list_item_main_event_icon_player);

        ((TextView)findViewById(R.id.list_item_main_event_player)).setText(pushMessage.getPlayer());
        if (pushMessage.getMessage() == "")
            findViewById(R.id.list_item_main_event_message).setVisibility(View.GONE);
        else
            ((TextView)findViewById(R.id.list_item_main_event_message)).setText(pushMessage.getMessage());

        ((TextView)findViewById(R.id.list_item_main_event_title)).setText(pushMessage.getTitle());
        eventTypeHelper.changeIconImages(pushMessage, bigIcon, playerIcon);
        //
        String time = String.format("%02d:%02d",pushMessage.getMinutes(),pushMessage.getSeconds())+"''";
        ((TextView)findViewById(R.id.list_item_main_event_time)).setText(time);
    }

    private void getScoreBoard() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://marroccl-909.appspot.com/game/get?gameId=" + pushMessage.getGameId() + "&minute=" + pushMessage.getMinutes() + "&second=" + pushMessage.getSeconds())
                .get()
                .build();

        scoreBoardContainer.setVisibility(View.GONE);
        loadingPartial.setVisibility(View.VISIBLE);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                Gson gson = new Gson();

                final GameItem gameItem = gson.fromJson(response.body().string(), GameItem.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ImageLoader.getInstance().displayImage(gameItem.getHomeTeamLink(), (ImageView) findViewById(R.id.score_board_image_rival_home));
                        ImageLoader.getInstance().displayImage(gameItem.getAwayTeamLink(), (ImageView) findViewById(R.id.score_board_image_rival_away));

                        ((TextView) findViewById(R.id.score_board_home_score)).setText(gameItem.getHomeTeamScore().toString());
                        ((TextView) findViewById(R.id.score_board_away_score)).setText(gameItem.getAwayTeamScore().toString());
                        loadingPartial.setVisibility(View.GONE);
                        scoreBoardContainer.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.event_menu, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        setShareIntent();

        // Return true to display menu
        return super.onCreateOptionsMenu(menu);
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

    public void setShareIntent() {
        if (shareActionProvider != null && pushMessage != null) {
            // populate the share intent with data
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, pushMessage.getTitle() + " / " + pushMessage.getMessage());
            intent.putExtra(Intent.EXTRA_TEXT, pushMessage.getVideoLink());
            shareActionProvider.setShareIntent(intent);
        }
    }
}
