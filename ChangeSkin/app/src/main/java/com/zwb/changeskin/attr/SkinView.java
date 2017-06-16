package com.zwb.changeskin.attr;

import android.view.View;

import java.util.List;

/**
 * Created by zwb
 * Description 需要换肤的view
 * Date 2017/6/16.
 */

public class SkinView {
    private View mView;
    private List<SkinAttr> mAttrs;

    public SkinView(View view, List<SkinAttr> attrs) {
        this.mView = view;
        this.mAttrs = attrs;
    }

    public View getView() {
        return mView;
    }

    public void setView(View view) {
        this.mView = view;
    }

    public List<SkinAttr> getAttrs() {
        return mAttrs;
    }

    public void setAttrs(List<SkinAttr> sttrs) {
        this.mAttrs = sttrs;
    }

    public void apply() {
        for (SkinAttr attr : mAttrs) {
            attr.apply(mView);
        }
    }
}
