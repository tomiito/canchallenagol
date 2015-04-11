package ar.com.ironsoft.marrocclandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ar.com.ironsoft.marrocclandroid.R;
import ar.com.ironsoft.marrocclandroid.domain.PushMessage;
import ar.com.ironsoft.marrocclandroid.helpers.EventTypeHelper;

/**
 * Created by gabrielvilloldo on 4/10/15.
 */
public class GameEventsAdapter extends ArrayAdapter<PushMessage> {
    protected ArrayList<PushMessage> pushedMessages;
    private EventTypeHelper eventTypeHelper;

    public GameEventsAdapter(int resource, ArrayList<PushMessage> pushedMessages, Context mContext) {
        super(mContext, resource, pushedMessages);
        this.pushedMessages = pushedMessages;
        eventTypeHelper = new EventTypeHelper();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PushMessage item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_main_event, parent, false);

            // configure view holder
            configureViewHolder(convertView);
        }

        final EventHolder eventHolder = (EventHolder) convertView.getTag();
        eventHolder.title.setTag(item);
        eventHolder.title.setText(item.getTitle());
        eventHolder.message.setText(item.getMessage());
        eventHolder.player.setText(item.getPlayer());
        eventHolder.time.setText(item.getMinutes() + ":" + item.getSeconds() + "''");
        if (position >= 1) {
            eventHolder.timeContainer.setBackgroundResource(R.color.gray_darker);
        }
        //
        eventTypeHelper.changeIconImages(item,eventHolder.image,eventHolder.playerIcon);

        return convertView;
    }

    @Override
    public int getCount() {
        return pushedMessages.size();
    }

    private void configureViewHolder(View convertView) {
        EventHolder eventHolder = new EventHolder();
        eventHolder.image = (ImageView)convertView.findViewById(R.id.list_item_main_event_image);
        eventHolder.playerIcon = (ImageView)convertView.findViewById(R.id.list_item_main_event_icon_player);
        eventHolder.title = (TextView)convertView.findViewById(R.id.list_item_main_event_title);
        eventHolder.message = (TextView)convertView.findViewById(R.id.list_item_main_event_message);
        eventHolder.player = (TextView)convertView.findViewById(R.id.list_item_main_event_player);
        eventHolder.time = (TextView)convertView.findViewById(R.id.list_item_main_event_time);
        eventHolder.timeContainer = (LinearLayout)convertView.findViewById(R.id.list_item_main_event_time_container);
        //
        convertView.setTag(eventHolder);
    }

    public static class EventHolder {
        public ImageView image;
        public TextView title;
        public TextView message;
        public ImageView playerIcon;
        public TextView player;
        public LinearLayout timeContainer;
        public TextView time;
    }
}
