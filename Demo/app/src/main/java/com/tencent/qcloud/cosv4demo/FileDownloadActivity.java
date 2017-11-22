package com.tencent.qcloud.cosv4demo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.tencent.qcloud.cosv4demo.Sample.BizService;
import com.tencent.qcloud.cosv4demo.Sample.GetObjectSample;

import java.io.File;

public class FileDownloadActivity extends AppCompatActivity implements View.OnClickListener{
    Button downloadBtn;
    TextView resultText;
    BizService bizService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_download);
        downloadBtn = (Button) findViewById(R.id.download);
        resultText = (TextView) findViewById(R.id.result);

        bizService = BizService.instance();
        bizService.init(getApplicationContext());

        downloadBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.download:
                onDownload();
                break;
        }
    }
    public void onDownload(){
        final String savePath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                File.separator + "test_download";
        final String downloadUrl = "http://xy2-1251668577.cosgz.myqcloud.com/www.jpg";
        resultText.setText("download_url :" + downloadUrl + "\n" + "savePath :" + savePath);
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetObjectSample.getObject(bizService,downloadUrl,savePath);
            }
        }).start();
    }
}
