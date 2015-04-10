package ar.com.ironsoft.marrocclandroid;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by gabrielvilloldo on 4/10/15.
 */
public class MarrocCLAndroidApplication extends Application {

    public static void initImageLoader(Context context) {
    // This configuration tuning is custom. You can tune every option, you may tune some of them,
    // or you can create default configuration by
    //  ImageLoaderConfiguration.createDefault(this);
    // method.
    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
            .denyCacheImageMultipleSizesInMemory()
            .diskCacheFileNameGenerator(new Md5FileNameGenerator())
            .diskCacheSize(150 * 1024 * 1024) // 150 Mb
            .memoryCacheExtraOptions(1280, 960)
            .diskCacheExtraOptions(1280, 960, null)
            .tasksProcessingOrder(QueueProcessingType.LIFO)
            .build();

    // Initialize ImageLoader with configuration.
    ImageLoader.getInstance().init(config);
    ImageLoader.getInstance().handleSlowNetwork(true);
}

    @Override
    public void onCreate() {
        super.onCreate();

        initImageLoader(getApplicationContext());
    }
}
