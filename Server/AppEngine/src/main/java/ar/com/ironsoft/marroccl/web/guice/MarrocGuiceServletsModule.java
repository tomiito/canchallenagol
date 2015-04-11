package ar.com.ironsoft.marroccl.web.guice;

import java.util.LinkedList;

import javax.inject.Singleton;

import ar.com.ironsoft.marroccl.web.app.guice.AppModule;
import ar.com.ironsoft.marroccl.web.app.modules.config.guice.ConfigModule;
import ar.com.ironsoft.marroccl.web.app.modules.devices.guice.DeviceModule;
import ar.com.ironsoft.marroccl.web.app.modules.game.guice.GameModule;
import ar.com.ironsoft.marroccl.web.app.modules.messages.guice.MessagesModule;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.inject.Provides;
import com.googlecode.objectify.ObjectifyFilter;
import com.googlecode.objectify.cache.AsyncCacheFilter;

/**
 * @author Tomas de Priede
 */
public class MarrocGuiceServletsModule extends GuiceServletsModule {

    @Override
    public LinkedList<Class<?>> getOfyClasses() {
        // USE THIS FOR IF YOU WANT TO STORE CURRENCIES
        // ObjectifyService.factory().getTranslators().add(new
        // BigDecimalLongTranslatorFactory());
        //
        return super.getOfyClasses();
    }

    @Provides
    @com.google.inject.Singleton
    public URLFetchService provideUrlFetchService() {
        return URLFetchServiceFactory.getURLFetchService();
    }

    @Provides
    @Singleton
    public BlobstoreService provideBlobstoreService() {
        return BlobstoreServiceFactory.getBlobstoreService();
    }

    @Provides
    @Singleton
    public ImagesService provideImagesService() {
        return ImagesServiceFactory.getImagesService();
    }

    @Override
    protected void configureServlets() {
        super.addModules(new AppModule(), new ConfigModule(),
                new DeviceModule(), new MessagesModule(), new GameModule());
        super.configureServlets();
        //
        filter("/*").through(AsyncCacheFilter.class);
        filter("/*").through(ObjectifyFilter.class);
        //
        bind(ObjectifyFilter.class).in(Singleton.class);
        bind(AsyncCacheFilter.class).in(Singleton.class);
    }
}
