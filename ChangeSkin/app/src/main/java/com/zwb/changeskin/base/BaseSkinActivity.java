package com.zwb.changeskin.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;

import com.zwb.changeskin.attr.SkinAttr;
import com.zwb.changeskin.attr.SkinAttrSupport;
import com.zwb.changeskin.attr.SkinView;
import com.zwb.changeskin.callback.ISkinChangedListener;
import com.zwb.changeskin.utils.ResourcesManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zwb
 * Description
 * Date 2017/6/15.
 */

public class BaseSkinActivity extends AppCompatActivity implements ISkinChangedListener {
    private final Object[] mConstructorArgs = new Object[2];
    private static final Map<String, Constructor<? extends View>> sConstructorMap
            = new ArrayMap<>();
    private static final Class<?>[] sConstructorSignature = new Class[]{
            Context.class, AttributeSet.class};
    private static final String[] sClassPrefixList = {
            "android.widget.",
            "android.view.",
            "android.webkit."
    };

    private static final Class<?>[] sCreateViewSignature = new Class[]{
            View.class, String.class, Context.class, AttributeSet.class};
    private Method mCreateViewMethod = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ResourcesManager.getInstance().registerListener(this);
        LayoutInflater inflater = getLayoutInflater();
        LayoutInflaterCompat.setFactory(inflater, new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                //appcompat 创建view代码
                AppCompatDelegate delegate = getDelegate();
//                view = delegate.createView(parent, name, context, attrs);
                View view = null;
                List<SkinAttr> skinAttrs;
                try {
                    if (mCreateViewMethod == null) {
                        mCreateViewMethod = delegate.getClass().getMethod("createView", sCreateViewSignature);
                    }
                    view = (View) mCreateViewMethod.invoke(delegate, parent, name, context, attrs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (view == null) {
                    view = createViewFromTag(context, name, attrs);
                }
                skinAttrs = SkinAttrSupport.getSkinAttrs(attrs, context);
                if (skinAttrs.isEmpty()) {
                    return null;
                }
                if (view != null) {
                    injectSkin(view, skinAttrs);
                }
                return view;
            }
        });
        super.onCreate(savedInstanceState);
    }

    private void injectSkin(View view, List<SkinAttr> skinAttrs) {
        List<SkinView> skinViews = ResourcesManager.getInstance().getSkinViews(this);
        if (skinViews == null) {
            skinViews = new ArrayList<>();
            ResourcesManager.getInstance().addSkinViews(this, skinViews);
        }
        skinViews.add(new SkinView(view, skinAttrs));

        //当前是否需要自动换肤
        // TODO: 2017/6/16
    }


    private View createViewFromTag(Context context, String name, AttributeSet attrs) {
        if (name.equals("view")) {
            name = attrs.getAttributeValue(null, "class");
        }

        try {
            mConstructorArgs[0] = context;
            mConstructorArgs[1] = attrs;

            if (-1 == name.indexOf('.')) {
                for (int i = 0; i < sClassPrefixList.length; i++) {
                    final View view = createView(context, name, sClassPrefixList[i]);
                    if (view != null) {
                        return view;
                    }
                }
                return null;
            } else {
                return createView(context, name, null);
            }
        } catch (Exception e) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            return null;
        } finally {
            // Don't retain references on context.
            mConstructorArgs[0] = null;
            mConstructorArgs[1] = null;
        }
    }


    private View createView(Context context, String name, String prefix)
            throws ClassNotFoundException, InflateException {
        Constructor<? extends View> constructor = sConstructorMap.get(name);

        try {
            if (constructor == null) {
                // Class not found in the cache, see if it's real, and try to add it
                Class<? extends View> clazz = context.getClassLoader().loadClass(
                        prefix != null ? (prefix + name) : name).asSubclass(View.class);

                constructor = clazz.getConstructor(sConstructorSignature);
                sConstructorMap.put(name, constructor);
            }
            constructor.setAccessible(true);
            return constructor.newInstance(mConstructorArgs);
        } catch (Exception e) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            return null;
        }
    }

    @Override
    public void change() {

    }

    @Override
    protected void onDestroy() {
        ResourcesManager.getInstance().unRegisterListener(this);
        super.onDestroy();
    }
}
