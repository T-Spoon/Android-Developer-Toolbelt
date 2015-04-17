package com.tspoon.androidtoolbelt.component.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;

import com.tspoon.androidtoolbelt.utils.MemoryUtils;

import java.util.ArrayList;

import timber.log.Timber;

public class MemoryService extends IntentService {

    private final Messenger mMessenger;

    private ArrayList<Byte[]> mAllocations = new ArrayList<>();
    private Boolean mRun;

    public MemoryService() {
        super(MemoryService.class.getName());
        mMessenger = new Messenger(new MessageHandler(mRun));
    }

    @Override
    public IBinder onBind(Intent intent) {
        Timber.d("onBind: " + intent);
        return mMessenger.getBinder();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mRun = true;
        while (mRun) {
            Timber.d("Attempting Allocation...");

            if (MemoryUtils.isMemoryAvailable()) {
                mAllocations.add(new Byte[1024 * 1024 * 5]);
                Timber.d("Allocated new block");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
