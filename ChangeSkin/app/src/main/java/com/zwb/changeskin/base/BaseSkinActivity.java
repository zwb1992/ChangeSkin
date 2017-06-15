package com.zwb.changeskin.base;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by zwb
 * Description
 * Date 2017/6/15.
 */

public class BaseSkinActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        LayoutInflater inflater = getLayoutInflater();
        LayoutInflaterCompat.setFactory(inflater, new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                Log.e("TAG", "name = " + name);
                int n = attrs.getAttributeCount();
                for (int i = 0; i < n; i++) {
                    Log.e("TAG", attrs.getAttributeName(i) + " , " + attrs.getAttributeValue(i));
                }
                if (name.equals("TextView")) {
                    return new EditText(context, attrs);
                }
                return null;
            }
        });
        super.onCreate(savedInstanceState, persistentState);
    }
}
