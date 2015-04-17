package com.tspoon.androidtoolbelt.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.regex.Pattern;

import timber.log.Timber;

public class MemoryUtils {

    private static final int MB = 1024 * 1024;

    private static MemoryUtils sInstance;


    private ActivityManager.MemoryInfo mMemoryInfo = new ActivityManager.MemoryInfo();
    private ActivityManager mActivityManager;

    private MemoryUtils(Context context) {
        mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        update();

        Timber.d(this.toString());
    }

    public static MemoryUtils get(Context context) {
        if (sInstance == null) {
            sInstance = new MemoryUtils(context);
        }
        return sInstance;
    }

    public void update() {
        mActivityManager.getMemoryInfo(mMemoryInfo);
    }

    public float getAvailableMemory() {
        return mMemoryInfo.availMem / MB;
    }

    public boolean isLowMemory() {
        return mMemoryInfo.lowMemory;
    }

    public long getMemoryThreshold() {
        return mMemoryInfo.threshold / MB;
    }

    public float getRamSize() {
        long memory;
        if (Build.VERSION.SDK_INT >= 16) {
            memory = mMemoryInfo.totalMem;
        } else {
            memory = readRamSizeFromSystem();
        }
        return memory / MB;
    }

    public float getFreeMemoryPercentage() {
        return (getAvailableMemory() / getRamSize()) * 100;
    }

    private static long readRamSizeFromSystem() {
        try {
            RandomAccessFile memFile = new RandomAccessFile("/proc/meminfo", "r");
            Pattern pattern = Pattern.compile("[0-9]+");

            memFile.close();
            String memory = pattern.matcher(memFile.readLine()).group();
            return Long.parseLong(memory) * 1024;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public String toString() {
        return "MemoryUtils{" +
                "availMem=" + mMemoryInfo.availMem +
                ", lowMemory=" + mMemoryInfo.lowMemory +
                ", threshold=" + mMemoryInfo.threshold +
                ", totalMem=" + getRamSize() +
                '}';
    }

    public static boolean isMemoryAvailable() {
        float freeMemoryPercent = 100 - (Runtime.getRuntime().totalMemory() / (float) Runtime.getRuntime().maxMemory()) * 100;
        if (freeMemoryPercent > 10) {
            return true;
        }
        return false;
    }
}
