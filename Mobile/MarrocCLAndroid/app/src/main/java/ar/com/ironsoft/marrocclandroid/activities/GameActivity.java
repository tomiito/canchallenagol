package ar.com.ironsoft.marrocclandroid.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import ar.com.ironsoft.marrocclandroid.R;
import ar.com.ironsoft.marrocclandroid.adapters.GameEventsAdapter;
import ar.com.ironsoft.marrocclandroid.domain.GameItem;
import ar.com.ironsoft.marrocclandroid.domain.PushMessage;
import ar.com.ironsoft.marrocclandroid.helpers.SharedPreferencesHelper;


public class GameActivity extends BaseActionBarActivity {

    private VideoView videoView;
    private ProgressBar progressBar;
    private ListView matchEvents;

    private GameEventsAdapter adapter;

    private Integer gameId;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        context = this;

        setUI();
        loadEventGames();
    }

    private void loadEventGames() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://marroccl-909.appspot.com/game/getfull?gameId=" + gameId)
                .get()
                .build();

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
                        ImageLoader.getInstance().displayImage(gameItem.homeTeamLink, (ImageView)findViewById(R.id.score_board_image_rival_home));
                        ImageLoader.getInstance().displayImage(gameItem.awayTeamLink, (ImageView)findViewById(R.id.score_board_image_rival_away));

                        ((TextView)findViewById(R.id.score_board_home_score)).setText(gameItem.homeTeamScore.toString());
                        ((TextView)findViewById(R.id.score_board_away_score)).setText(gameItem.awayTeamScore.toString());
                    }
                });
            }
        });

        Gson gson = new Gson();
        ArrayList<PushMessage> events = new ArrayList<>();
        //for (String value : recordsSaved) {
        //    events.add(gson.fromJson(value, PushMessage.class));
        //}
        adapter = new GameEventsAdapter(0, new ArrayList(events), this);
        matchEvents.setAdapter(adapter);

    }

    private void setUI() {
        videoView = (VideoView)findViewById(R.id.video);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        matchEvents = (ListView)findViewById(R.id.match_events);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return true;
    }


}
