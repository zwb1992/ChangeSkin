package com.zwb.changeskin.attr;

import android.view.View;

/**
 * Created by zwb
 * Description 需要换肤的属性
 * Date 2017/6/16.
 */

public class SkinAttr {
    private String mResName;
    private SkinAttrType mType;

    public SkinAttr(String mResName, SkinAttrType mType) {
        this.mResName = mResName;
        this.mType = mType;
    }

    public void apply(View view) {
        mType.apply(view, mResName);
    }
}
