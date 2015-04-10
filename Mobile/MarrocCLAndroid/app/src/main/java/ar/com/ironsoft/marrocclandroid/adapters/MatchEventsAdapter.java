package ar.com.ironsoft.marrocclandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ar.com.ironsoft.marrocclandroid.R;
import ar.com.ironsoft.marrocclandroid.domain.PushMessage;

/**
 * Created by gabrielvilloldo on 4/10/15.
 */
public class MatchEventsAdapter extends ArrayAdapter<PushMessage> {
    protected ArrayList<PushMessage> pushedMessages;

    private Context mContext;

    public MatchEventsAdapter(Context context, int resource, ArrayList<PushMessage> pushedMessages, Context mContext) {
        super(context, resource);
        this.pushedMessages = pushedMessages;
        this.mContext = mContext;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PushMessage item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_main_event, parent, false);

            // configure view holder
            configureViewHolder(convertView);
        }

        final EventHolder listingHolder = (EventHolder) convertView.getTag();
        listingHolder.image.setImageResource(R.drawable.ic_ball); // TODO: GV Change with Type.
        listingHolder.title.setText(item.getTitle());
        listingHolder.message.setText(item.getMessage());

        return convertView;
    }

    @Override
    public int getCount() {
        return pushedMessages.size();
    }

    private void configureViewHolder(View convertView) {
        EventHolder eventHolder = new EventHolder();
        eventHolder.image = (ImageView)convertView.findViewById(R.id.list_item_main_event_image);
        eventHolder.title = (TextView)convertView.findViewById(R.id.list_item_main_event_title);
        eventHolder.message = (TextView)convertView.findViewById(R.id.list_item_main_event_message);
        convertView.setTag(eventHolder);
    }

    public static class EventHolder {
        public ImageView image;
        public TextView title;
        public TextView message;
    }
}
