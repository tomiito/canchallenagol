package ar.com.ironsoft.marrocclandroid.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import ar.com.ironsoft.marrocclandroid.R;
import ar.com.ironsoft.marrocclandroid.adapters.GameEventsAdapter;
import ar.com.ironsoft.marrocclandroid.domain.GameFullItem;
import ar.com.ironsoft.marrocclandroid.domain.GameItem;
import ar.com.ironsoft.marrocclandroid.domain.PushMessage;
import ar.com.ironsoft.marrocclandroid.helpers.SharedPreferencesHelper;


public class GameActivity extends BaseActionBarActivity {

    private ListView gameEvents;
    private View loading;
    private View container;

    private GameEventsAdapter adapter;

    private String gameId;

    Context context;

    private DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameId = getIntent().getStringExtra("gameId");
        loading = findViewById(R.id.loading_circular_fullscreen_container);
        container = findViewById(R.id.game_container);
        options = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageForEmptyUri(R.drawable.ic_ball)
                .showImageOnFail(R.drawable.ic_ball)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(300, true, true, false))
                .build();
        context = this;

        setUI();
        loadEventsGame();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loading.setVisibility(View.VISIBLE);
        container.setVisibility(View.GONE);
        loadEventsGame();
    }


    private void loadEventsGame() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://marroccl-909.appspot.com/game/getFull?gameId=" + gameId)
                .get()
                .build();

        loading.setVisibility(View.VISIBLE);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                Gson gson = new Gson();

                final GameFullItem gameFullItem = gson.fromJson(response.body().string(), GameFullItem.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ImageLoader.getInstance().displayImage(gameFullItem.getHomeTeamLink(), (ImageView)findViewById(R.id.score_board_image_rival_home), options);
                        ImageLoader.getInstance().displayImage(gameFullItem.getAwayTeamLink(), (ImageView) findViewById(R.id.score_board_image_rival_away), options);

                        ((TextView)findViewById(R.id.score_board_home_score)).setText(gameFullItem.getHomeTeamScore().toString());
                        ((TextView)findViewById(R.id.score_board_away_score)).setText(gameFullItem.getAwayTeamScore().toString());

                        adapter = new GameEventsAdapter(0, gameFullItem.getMessages(), context);
                        gameEvents.setAdapter(adapter);
                        loading.setVisibility(View.GONE);
                        container.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }

    private void setUI() {
        gameEvents = (ListView)findViewById(R.id.game_events);
        gameEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent().setClass(context, EventActivity.class);

                intent.putExtra("pushMessage", (PushMessage) view.findViewById(R.id.list_item_main_event_title).getTag());
                context.startActivity(intent);
            }
        });
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
