package com.tspoon.androidtoolbelt.compiler;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class Registry {

    private static Registry sInstance;

    private Messager mMessager;
    private Types mTypeUtils;
    private Elements mElementUtils;
    private Filer mFiler;

    public static void init(Messager messager, Types types, Elements elements, Filer filer) {
        sInstance = new Registry(messager, types, elements, filer);
    }

    public static Registry get() {
        return sInstance;
    }

    private Registry(Messager messager, Types types, Elements elements, Filer filer) {
        mMessager = messager;
        mTypeUtils = types;
        mElementUtils = elements;
        mFiler = filer;
    }

    public Messager getMessager() {
        return mMessager;
    }

    public Types getTypeUtils() {
        return mTypeUtils;
    }

    public Elements getElementUtils() {
        return mElementUtils;
    }

    public Filer getFiler() {
        return mFiler;
    }
}
