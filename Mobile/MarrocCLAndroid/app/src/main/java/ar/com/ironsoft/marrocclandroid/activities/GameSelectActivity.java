package ar.com.ironsoft.marrocclandroid.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

import ar.com.ironsoft.marrocclandroid.R;
import ar.com.ironsoft.marrocclandroid.adapters.GameEventsAdapter;
import ar.com.ironsoft.marrocclandroid.adapters.GameListAdapter;
import ar.com.ironsoft.marrocclandroid.domain.GameItem;
import ar.com.ironsoft.marrocclandroid.domain.PushMessage;
import ar.com.ironsoft.marrocclandroid.listeners.OnSingleClickListener;


public class GameSelectActivity extends BaseActionBarActivity {

    private ListView gamesList;
    private View loading;

    private GameListAdapter adapter;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_select);

        context = this;
        gamesList = (ListView)findViewById(R.id.games_list);
        loading = findViewById(R.id.loading_circular_fullscreen_container);
        gamesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent().setClass(context, GameActivity.class);

                intent.putExtra("gameId", (String) view.findViewById(R.id.list_item_main_game_home_score_name).getTag());
                context.startActivity(intent);
            }
        });

        getGames();
    }

    private void getGames() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://marroccl-909.appspot.com/game/list")
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

                GameItem[] gameItems = gson.fromJson(response.body().string(), GameItem[].class);

                ArrayList<GameItem> games = new ArrayList<>();
                for (GameItem value : gameItems) {
                    games.add(value);
                }
                adapter = new GameListAdapter(0, games, context);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gamesList.setAdapter(adapter);
                        loading.setVisibility(View.GONE);
                        gamesList.setVisibility(View.VISIBLE);
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
        }
        return true;
    }
}
