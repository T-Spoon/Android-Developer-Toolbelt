package com.tspoon.androidtoolbelt.component.service;

import android.content.Context;

public interface ServiceHolder {

    public static final String QUALIFIED_NAME = ServiceHolder.class.getCanonicalName() + "Impl";

    public void startServices(Context context);

    public void stopServices();


}
