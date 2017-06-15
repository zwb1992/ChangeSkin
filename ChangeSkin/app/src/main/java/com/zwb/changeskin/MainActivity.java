package com.zwb.changeskin;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.zwb.changeskin.utils.ResourcesManager;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String apkPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "skin_plug-debug.apk";
    private static final String packageName = "com.zwb.skin_plug";

    private RelativeLayout mainLayout;
    private Button plug_skin, reset_skin, test_factory;
    private ResourcesManager resourcesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = (RelativeLayout) findViewById(R.id.activity_main);
        plug_skin = (Button) findViewById(R.id.plug_skin);
        plug_skin.setOnClickListener(this);
        reset_skin = (Button) findViewById(R.id.reset_skin);
        reset_skin.setOnClickListener(this);
        test_factory = (Button) findViewById(R.id.test_factory);
        test_factory.setOnClickListener(this);
        resourcesManager = new ResourcesManager(apkPath, packageName, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plug_skin:
                Drawable drawable = resourcesManager.getDrawableByName("skin_image_bg");
                if (drawable != null) {
                    mainLayout.setBackground(drawable);
                }
                break;
            case R.id.reset_skin:
                mainLayout.setBackgroundResource(R.mipmap.skin_image_bg);
                break;
            case R.id.test_factory:
                startActivity(new Intent(this, TestFactoryActivity.class));
                break;
        }
    }
}
