package com.zwb.changeskin.attr;

import android.content.Context;
import android.util.AttributeSet;

import com.zwb.changeskin.config.Const;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zwb
 * Description
 * Date 2017/6/16.
 */

public class SkinAttrSupport {

    public static List<SkinAttr> getSkinAttrs(AttributeSet attrs, Context context) {
        List<SkinAttr> mSkinAttrs = new ArrayList<>();
        SkinAttrType attrType;
        SkinAttr skinAttr;
        for (int i = 0, n = attrs.getAttributeCount(); i < n; i++) {
            String attrName = attrs.getAttributeName(i);
            String attrVal = attrs.getAttributeValue(i);
            if (attrVal.startsWith("@")) {//说明是引用资源
                int id = -1;
                try {
                    id = Integer.parseInt(attrVal.substring(1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (id == -1) {
                    continue;
                }
                String resName = context.getResources().getResourceEntryName(id);
                if (resName.startsWith(Const.SKIN_PREFIX)) {
                    attrType = getAttrType(attrName);
                    if (attrType == null) continue;
                    skinAttr = new SkinAttr(resName, attrType);
                    mSkinAttrs.add(skinAttr);
                }
            }
        }
        return mSkinAttrs;
    }

    private static SkinAttrType getAttrType(String attrName) {
        for (SkinAttrType attrType : SkinAttrType.values()) {
            if (attrType.getResType().equals(attrName)) {
                return attrType;
            }
        }
        return null;
    }

}
