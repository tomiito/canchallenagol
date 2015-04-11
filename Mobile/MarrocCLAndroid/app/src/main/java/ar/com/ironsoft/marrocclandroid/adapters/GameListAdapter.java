package ar.com.ironsoft.marrocclandroid.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

import ar.com.ironsoft.marrocclandroid.R;
import ar.com.ironsoft.marrocclandroid.domain.GameItem;

/**
 * Created by gabrielvilloldo on 4/10/15.
 */
public class GameListAdapter extends ArrayAdapter<GameItem> {
    protected ArrayList<GameItem> games;
    private DisplayImageOptions options;

    public GameListAdapter(int resource, ArrayList<GameItem> games, Context mContext) {
        super(mContext, resource, games);
        this.games = games;

        options = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageForEmptyUri(R.drawable.ic_ball)
                .showImageOnFail(R.drawable.ic_ball)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(300, true, true, false))
                .build();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final GameItem item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_main_game, parent, false);

            // configure view holder
            configureViewHolder(convertView);
        }

        final GameHolder gameHolder = (GameHolder) convertView.getTag();

        ImageLoader.getInstance().displayImage(item.getHomeTeamLink(), gameHolder.imageHome, options);
        ImageLoader.getInstance().displayImage(item.getAwayTeamLink(), gameHolder.imageAway, options);
        gameHolder.awayName.setTag(item.getStatus());
        gameHolder.homeName.setTag(item.getGameId());
        gameHolder.homeName.setText(item.getHomeTeamName());
        gameHolder.awayName.setText(item.getAwayTeamName());

        if (item.getStatus() == 1) {
            gameHolder.homeScore.setText("");
            gameHolder.awayScore.setText("");
            gameHolder.status.setText("No comenzado");
        } else {
            gameHolder.homeScore.setText(item.getHomeTeamScore().toString());
            gameHolder.awayScore.setText(item.getAwayTeamScore().toString());
            if (item.getStatus() == 2)
                gameHolder.status.setText(item.getCurrentMinute().toString() + ":" + item.getCurrentSecond().toString());
            else
                gameHolder.status.setText("Finalizado");
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return games.size();
    }

    private void configureViewHolder(View convertView) {
        GameHolder gameHolder = new GameHolder();

        gameHolder.imageHome = (ImageView)convertView.findViewById(R.id.list_item_main_game_image_rival_home);
        gameHolder.imageAway = (ImageView)convertView.findViewById(R.id.list_item_main_game_image_rival_away);

        gameHolder.homeName = (TextView)convertView.findViewById(R.id.list_item_main_game_home_score_name);
        gameHolder.awayName = (TextView)convertView.findViewById(R.id.list_item_main_game_away_score_name);

        gameHolder.homeScore = (TextView)convertView.findViewById(R.id.list_item_main_game_home_score);
        gameHolder.awayScore = (TextView)convertView.findViewById(R.id.list_item_main_game_away_score);
        gameHolder.status = (TextView)convertView.findViewById(R.id.list_item_main_game_status);

        convertView.setTag(gameHolder);
    }

    public static class GameHolder {

        public ImageView imageHome;
        public ImageView imageAway;
        public TextView homeName;
        public TextView awayName;
        public TextView homeScore;
        public TextView awayScore;
        public TextView status;

    }
}
