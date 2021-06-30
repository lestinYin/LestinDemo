package com.pensees.guard.face.activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.pensees.guard.R;
import com.pensees.guard.face.service.FloatingButtonService;

import ai.pensees.commons.SystemUtils;

public class NewSplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_splash);

        SystemUtils.sendSysCmd("am start -n sg.gov.tech.safeentry/.MainActivity;\n" +
                "am start -n com.pes.smartguard.normal/com.pes.smartguard.business.module.frame.SplashActivity --stack 3");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (!Settings.canDrawOverlays(NewSplashActivity.this)) {
            Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
            startService(new Intent(NewSplashActivity.this, FloatingButtonService.class));
        }
    }
}
