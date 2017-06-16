package com.zwb.changeskin;

import android.app.Application;

import com.zwb.changeskin.utils.ResourcesManager;

/**
 * Created by zwb
 * Description
 * Date 2017/6/16.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ResourcesManager.getInstance().init(this);
    }
}
