package ar.com.ironsoft.marrocclandroid.listeners;

import android.os.SystemClock;
import android.view.View;

/**
 * Created by gvilloldo on 11/04/2015.
 */
public abstract class OnSingleClickListener implements View.OnClickListener {
    private static final long THRESHOLD_MILLIS = 350l;
    private long lastClickMillis;
    private boolean clickable = true;

    /**
     * Override onClick() instead.
     */
    @Override
    public final void onClick(View v) {
        long now = SystemClock.elapsedRealtime();
        if (clickable && (now - lastClickMillis > THRESHOLD_MILLIS)) {
            clickable = false;
            onSingleClick(v);
            reset();
        }
        lastClickMillis = now;
    }

    /**
     * Override this function to handle clicks.
     * reset() must be called after each click for this function to be called
     * again.
     *
     * @param v
     */
    public abstract void onSingleClick(View v);

    /**
     * Allows another click.
     */
    public void reset() {
        clickable = true;
    }
}
