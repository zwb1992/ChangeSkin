package com.zwb.changeskin.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwb.changeskin.utils.ResourcesManager;

/**
 * Created by zwb
 * Description 需要换肤的属性的类型
 * Date 2017/6/16.
 */

public enum SkinAttrType {
    BACKGROUND("background") {
        public void apply(View view, String resName) {
            Drawable drawable = getResourcesManager().getDrawableByName(resName);
            if (drawable != null) {
                view.setBackgroundDrawable(drawable);
            }
        }
    }, SRC("src") {
        public void apply(View view, String resName) {
            Drawable drawable = getResourcesManager().getDrawableByName(resName);
            if (drawable != null) {
                if (view instanceof ImageView) {
                    ImageView imageView = (ImageView) view;
                    imageView.setImageDrawable(drawable);
                }
            }
        }
    }, TEXT_COLOR("textColor") {
        public void apply(View view, String resName) {
            ColorStateList colorStateList = getResourcesManager().getColorByName(resName);
            if (colorStateList != null) {
                if (view instanceof TextView) {
                    TextView textView = (TextView) view;
                    textView.setTextColor(colorStateList);
                }
            }
        }
    };

    private String resType;

    public String getResType() {
        return resType;
    }

    SkinAttrType(String resType) {
        this.resType = resType;
    }

    public abstract void apply(View view, String resName);

    public ResourcesManager getResourcesManager() {
        return ResourcesManager.getInstance();
    }
}
