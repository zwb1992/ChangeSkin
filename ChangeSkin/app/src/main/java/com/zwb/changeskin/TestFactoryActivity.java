package com.zwb.changeskin;

import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class TestFactoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_factory);
    }
}
