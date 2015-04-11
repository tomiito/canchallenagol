package ar.com.ironsoft.marrocclandroid.helpers;

import android.widget.ImageView;

import ar.com.ironsoft.marrocclandroid.R;
import ar.com.ironsoft.marrocclandroid.domain.PushMessage;

/**
 * Created by tomiito on 4/11/15.
 */
public class EventTypeHelper {
    public void changeIconImages(PushMessage pushMessage, ImageView bigIcon, ImageView playerIcon) {
        switch (pushMessage.getType().toLowerCase()) {
            case "goal":
                bigIcon .setImageResource(R.drawable.goal_big);
                break;
            case "yellow card":
                bigIcon .setImageResource(R.drawable.yellow_card_big);
                playerIcon.setImageResource(R.drawable.yellow_card);
                break;
            case "red card":
                bigIcon.setImageResource(R.drawable.red_card_big);
                playerIcon.setImageResource(R.drawable.red_card);
                break;
            case "corner":
                bigIcon .setImageResource(R.drawable.red_card_big);
                break;
            case "offside":
                bigIcon.setImageResource(R.drawable.red_card_big);
                break;
            default:
                // Put something
                bigIcon.setImageResource(R.drawable.ic_ball);
                playerIcon.setImageResource(R.drawable.player_goal);
                break;
        }
    }
}
