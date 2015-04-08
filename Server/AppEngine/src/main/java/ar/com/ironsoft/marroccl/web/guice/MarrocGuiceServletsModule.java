package ar.com.ironsoft.marroccl.web.guice;

import javax.inject.Singleton;

import ar.com.ironsoft.marroccl.web.app.guice.AppModule;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.inject.Provides;

/**
 * @author Tomas de Priede
 */
public class MarrocGuiceServletsModule extends GuiceServletsModule {

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
        super.addModules(new AppModule());
        super.configureServlets();
    }
}
