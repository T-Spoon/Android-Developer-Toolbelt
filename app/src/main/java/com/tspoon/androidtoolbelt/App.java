package com.tspoon.androidtoolbelt;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.tspoon.androidtoolbelt.component.service.ServiceHolder;

import timber.log.Timber;

public class App extends Application {

    private static ServiceHolder sServiceHolder;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        try {
            Class adapterClass = Class.forName(ServiceHolder.QUALIFIED_NAME);
            sServiceHolder = (ServiceHolder) adapterClass.newInstance();
        } catch (Exception e) {
            Timber.e(e, "Error initializing ServiceHolder.");
            Crashlytics.logException(e);
        }
    }

    public static ServiceHolder getServiceHolder() {
        return sServiceHolder;
    }
}
