package com.tspoon.androidtoolbelt.utils;

import android.content.res.Resources;
import android.util.TypedValue;

public class Utils {

    public static int dpToPixels(Resources resources, float pixels) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixels, resources.getDisplayMetrics());
    }
}
