package com.zwb.changeskin;

import android.content.Intent;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zwb.changeskin.base.BaseSkinActivity;
import com.zwb.changeskin.callback.ISkinChangingCallback;
import com.zwb.changeskin.utils.ResourcesManager;

import java.io.File;

public class MainActivity extends BaseSkinActivity implements View.OnClickListener {
    private static final String apkPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "skin_plug-debug.apk";
    private static final String packageName = "com.zwb.skin_plug";

    private LinearLayout mainLayout;
    private Button plug_skin, reset_skin, test_factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = (LinearLayout) findViewById(R.id.activity_main);
        plug_skin = (Button) findViewById(R.id.plug_skin);
        plug_skin.setOnClickListener(this);
        reset_skin = (Button) findViewById(R.id.reset_skin);
        reset_skin.setOnClickListener(this);
        test_factory = (Button) findViewById(R.id.test_factory);
        test_factory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plug_skin:
                ResourcesManager.getInstance().changeSkin(apkPath, packageName, new ISkinChangingCallback() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onError() {
                        Log.e("info", "--onError---");
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(MainActivity.this, "换肤成功", Toast.LENGTH_SHORT).show();
                    }
                });
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
