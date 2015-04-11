package ar.com.ironsoft.marrocclandroid.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import ar.com.ironsoft.marrocclandroid.R;
import ar.com.ironsoft.marrocclandroid.adapters.MatchEventsAdapter;
import ar.com.ironsoft.marrocclandroid.domain.PushMessage;
import ar.com.ironsoft.marrocclandroid.helpers.SharedPreferencesHelper;


public class MatchSelectActivity extends Activity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_select);

        context = this;

        getMatches();
    }

    private void getMatches() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://marroccl-909.appspot.com/matches/")
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
}
