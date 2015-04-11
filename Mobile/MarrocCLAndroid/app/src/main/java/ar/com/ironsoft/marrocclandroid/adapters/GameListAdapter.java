package ar.com.ironsoft.marrocclandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.games.Game;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import ar.com.ironsoft.marrocclandroid.R;
import ar.com.ironsoft.marrocclandroid.domain.GameItem;
import ar.com.ironsoft.marrocclandroid.domain.PushMessage;

/**
 * Created by gabrielvilloldo on 4/10/15.
 */
public class GameListAdapter extends ArrayAdapter<GameItem> {
    protected ArrayList<GameItem> games;

    public GameListAdapter(int resource, ArrayList<GameItem> games, Context mContext) {
        super(mContext, resource, games);
        this.games = games;
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

        ImageLoader.getInstance().displayImage(item.getHomeTeamLink(), gameHolder.imageHome);
        ImageLoader.getInstance().displayImage(item.getAwayTeamLink(), gameHolder.imageAway);
        gameHolder.awayName.setTag(item.getStatus());
        gameHolder.homeName.setTag(item.getGameId());
        gameHolder.homeName.setText(item.getHomeTeamName());
        gameHolder.awayName.setText(item.getAwayTeamName());

        if (item.getStatus() != 2) {
            gameHolder.homeScore.setText("");
            gameHolder.awayScore.setText("");
            if (item.getStatus() == 1) {
                gameHolder.status.setText("No comenzado");
            }
            else {
                gameHolder.status.setText("Finalizado");

            }
        } else {
            gameHolder.homeScore.setText(item.getHomeTeamScore().toString());
            gameHolder.awayScore.setText(item.getAwayTeamScore().toString());
            gameHolder.status.setText(item.getCurrentMinute().toString() + ":" + item.getCurrentSecond().toString());
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
