package com.tspoon.androidtoolbelt.component.service;

import android.os.Handler;
import android.os.Message;

import timber.log.Timber;

public class MessageHandler extends Handler {

    public static final String KEY_MESSAGE = "KEY_MESSAGE";
    public static final String MSG_STOP = "STOP";

    private Boolean mRun;

    MessageHandler(Boolean run) {
        mRun = run;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (msg.getData() != null) {
            String message = msg.getData().getString(KEY_MESSAGE);
            Timber.d("Service received message: " + message);
            switch (message) {
                case MSG_STOP:
                    mRun = false;
                    break;
            }
        }
    }
}