package com.tspoon.androidtoolbelt.component.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class ServiceHolderImplSample implements ServiceHolder {

    private static final List<Class<? extends Service>> SERVICES = new ArrayList<Class<? extends Service>>() {{
        add(MemoryService.class);
    }};
    private static final List<MemoryServiceConnection> CONNECTIONS = new ArrayList<>();

    @Override
    public void startServices(Context context) {
        for (Class<? extends Service> service : SERVICES) {
            Intent intent = new Intent(context, service);
            context.startService(intent);

            MemoryServiceConnection connection = new MemoryServiceConnection();
            context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    public void stopServices() {
        Timber.d("Stopping Services... " + CONNECTIONS.size() + " connections found.");
        while (CONNECTIONS.size() > 0) {
            CONNECTIONS.remove(0).stopService();
        }
    }
}
