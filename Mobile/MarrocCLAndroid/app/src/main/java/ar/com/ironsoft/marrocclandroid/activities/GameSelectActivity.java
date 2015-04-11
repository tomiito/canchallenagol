package ar.com.ironsoft.marrocclandroid.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import ar.com.ironsoft.marrocclandroid.R;


public class GameSelectActivity extends BaseActionBarActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_select);

        context = this;

        getGames();
    }

    private void getGames() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://marroccl-909.appspot.com/games/list")
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

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
