package com.tspoon.androidtoolbelt.component.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import timber.log.Timber;

public class MemoryServiceConnection implements ServiceConnection {

    private Messenger mMessenger;
    private boolean mConnected;

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mMessenger = new Messenger(service);
        mConnected = true;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mMessenger = null;
        mConnected = false;
    }

    public void stopService() {
        if (mConnected) {
            Message message = Message.obtain();

            Bundle data = new Bundle();
            data.putString(MessageHandler.KEY_MESSAGE, MessageHandler.MSG_STOP);
            message.setData(data);
            try {
                mMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        } else {
            Timber.w("Attempting to stop a service that's not connected.");
        }
    }
}
