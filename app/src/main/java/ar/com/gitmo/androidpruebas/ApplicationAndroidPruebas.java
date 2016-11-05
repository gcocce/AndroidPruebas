package ar.com.gitmo.androidpruebas;

import timber.log.Timber;

/**
 * Created by Gitmo on 05/11/2016.
 */

public class ApplicationAndroidPruebas extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // ...
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

    }
}
