package com.zwb.changeskin.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.zwb.changeskin.attr.SkinView;
import com.zwb.changeskin.callback.ISkinChangedListener;
import com.zwb.changeskin.callback.ISkinChangingCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zwb
 * Description
 * Date 2017/6/15.
 */

public class ResourcesManager {
    private String apkPath;
    private String packageName;
    private Resources resources;
    private Context mContext;
    private static ResourcesManager instance;
    private Map<ISkinChangedListener, List<SkinView>> mSkinViewMap = new HashMap<>();
    private List<ISkinChangedListener> mListeners = new ArrayList<>();
    private PrefUtils prefUtils;

    public static ResourcesManager getInstance() {
        if (instance == null) {
            synchronized (ResourcesManager.class) {
                if (instance == null) {
                    instance = new ResourcesManager();
                }
            }
        }
        return instance;
    }

    public void init(Context mContext) {
        this.mContext = mContext;
        prefUtils = new PrefUtils(mContext);
        try {
            String pluginPath = prefUtils.getPath();
            String pluginPkg = prefUtils.getPkg();
            File file = new File(pluginPath);
            if (file.exists()) {
                loadPlugin(pluginPath, pluginPkg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            prefUtils.clear();
        }
    }


    private ResourcesManager() {

    }


    private void loadPlugin(String apkPath, String packageName) throws Exception {
        if (apkPath.equals(this.apkPath) && packageName.equals(this.packageName)) {
            return;
        }
        AssetManager assetManager = AssetManager.class.newInstance();
        AssetManager.class.getDeclaredMethod("addAssetPath", String.class).invoke(assetManager, apkPath);
        resources = new Resources(assetManager,
                mContext.getResources().getDisplayMetrics(),
                mContext.getResources().getConfiguration());
        this.apkPath = apkPath;
        this.packageName = packageName;
    }

    /**
     * 获取图片资源
     *
     * @param name 图片名称
     * @return
     */
    public Drawable getDrawableByName(String name) {
        if (resources != null) {
            return resources.getDrawable(resources.getIdentifier(name, "mipmap", packageName));
        }
        return null;
    }

    public ColorStateList getColorByName(String name) {
        if (resources != null) {
            return resources.getColorStateList(resources.getIdentifier(name, "color", packageName));
        }
        return null;
    }

    public List<SkinView> getSkinViews(ISkinChangedListener listener) {
        return mSkinViewMap.get(listener);
    }

    public void addSkinViews(ISkinChangedListener listener, List<SkinView> skinViews) {
        mSkinViewMap.put(listener, skinViews);
    }

    public void registerListener(ISkinChangedListener listener) {
        mListeners.add(listener);
    }

    public void unRegisterListener(ISkinChangedListener listener) {
        mListeners.remove(listener);
        mSkinViewMap.remove(listener);
    }

    public void changeSkin(final String apkPath, final String packageName, ISkinChangingCallback callback) {
        if (callback == null) {
            callback = ISkinChangingCallback.DEFAULT_SKIN_CHANGING_CALLBACK;
        }

        final ISkinChangingCallback finalCallback = callback;
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    loadPlugin(apkPath, packageName);
                } catch (Exception e) {
                    finalCallback.onError();
                    prefUtils.clear();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                try {
                    notifyChangedListener();
                    finalCallback.onComplete();
                    prefUtils.savePath(apkPath);
                    prefUtils.savePkg(packageName);
                } catch (Exception e) {
                    e.printStackTrace();
                    finalCallback.onError();
                    prefUtils.clear();
                }
            }
        }.execute();
    }

    private void notifyChangedListener() {
        for (ISkinChangedListener listener : mListeners) {
            skinChanged(listener);
            listener.change();
        }
    }

    public void skinChanged(ISkinChangedListener listener) {
        List<SkinView> skinViews = mSkinViewMap.get(listener);
        for (SkinView skinView : skinViews) {
            skinView.apply();
        }
    }

    public boolean needChange() {
        return !TextUtils.isEmpty(apkPath);
    }
}
