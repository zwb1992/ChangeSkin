package com.zwb.changeskin.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.zwb.changeskin.config.Const;

/**
 * Created by zwb
 * Description
 * Date 2017/6/16.
 */

public class PrefUtils {

    private Context mContex;

    public PrefUtils(Context mContex) {
        this.mContex = mContex;
    }

    public void savePath(String path) {
        SharedPreferences sp = mContex.getSharedPreferences(Const.PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(Const.PLUGIN_PATH, path).apply();
    }

    public String getPath() {
        SharedPreferences sp = mContex.getSharedPreferences(Const.PREF_NAME, Context.MODE_PRIVATE);
        return sp.getString(Const.PLUGIN_PATH, null);
    }

    public void savePkg(String pkg) {
        SharedPreferences sp = mContex.getSharedPreferences(Const.PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(Const.PLUGIN_PKG, pkg).apply();
    }

    public String getPkg() {
        SharedPreferences sp = mContex.getSharedPreferences(Const.PREF_NAME, Context.MODE_PRIVATE);
        return sp.getString(Const.PLUGIN_PKG, null);
    }

    public void clear() {
        SharedPreferences sp = mContex.getSharedPreferences(Const.PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().clear().apply();
    }
}
