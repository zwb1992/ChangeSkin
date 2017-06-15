package com.zwb.changeskin.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

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

    public ResourcesManager(String apkPath, String packageName, Context mContext) {
        this.apkPath = apkPath;
        this.packageName = packageName;
        this.mContext = mContext;
        this.resources = getResources(mContext, apkPath);
    }


    private Resources getResources(Context context, String apkPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            AssetManager.class.getDeclaredMethod("addAssetPath", String.class).invoke(assetManager, apkPath);
            return new Resources(assetManager,
                    context.getResources().getDisplayMetrics(),
                    context.getResources().getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
}
