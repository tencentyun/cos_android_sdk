package com.tencent.qcloud.cosv4demo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button fileBtn;
    Button dirBtn;
    Button downloadBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fileBtn = (Button)findViewById(R.id.file);
        dirBtn = (Button)findViewById(R.id.dir);
        downloadBtn = (Button)findViewById(R.id.download);

        fileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("XIAO","文件测试");
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, FileUploadActivity.class);
                startActivity(intent);

            }
        });
        dirBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("XIAO","文件夹测试");
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DirActivity.class);
                startActivity(intent);
            }
        });

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("XIAO","下载测试");
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, FileDownloadActivity.class);
                startActivity(intent);
            }
        });

        grantPermission();
    }

    //grant permission for app
    private void grantPermission(){
        if(Build.VERSION.SDK_INT >= 23){
            int granted = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    + checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    + checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE)
                    + checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE);
            if(granted != PackageManager.PERMISSION_GRANTED){
                //grant
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_NETWORK_STATE,
                                Manifest.permission.ACCESS_WIFI_STATE}, 0);
            }
        }
    }
}
