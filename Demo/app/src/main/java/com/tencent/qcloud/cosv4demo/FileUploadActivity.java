package com.tencent.qcloud.cosv4demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.cos.utils.FileUtils;
import com.tencent.qcloud.cosv4demo.Sample.BizService;
import com.tencent.qcloud.cosv4demo.Sample.GetObjeceMetadataSample;
import com.tencent.qcloud.cosv4demo.Sample.PutObjectSample;
import com.tencent.qcloud.cosv4demo.Sample.UpdateObjectSample;

public class FileUploadActivity extends AppCompatActivity implements View.OnClickListener{
    Button addBtn, uploadBtn;
    TextView localText, resultText;
    static final int OPENFILE_CODE = 1;
    String currentPath = null;

    BizService bizService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_upload);
        addBtn = (Button) findViewById(R.id.add);
        uploadBtn = (Button) findViewById(R.id.upload);
        localText = (TextView) findViewById(R.id.local);
        resultText = (TextView) findViewById(R.id.result);

        //初始化 cosClient
        bizService = BizService.instance();
        bizService.init(getApplicationContext());

        addBtn.setOnClickListener(this);
        uploadBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id) {
            case R.id.add:
                onAdd();
                break;
            case R.id.upload:
               upload();
                //updateMeta();
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK || data == null){
            return;
        }
        switch(requestCode){
            case OPENFILE_CODE:
                Uri uri = data.getData();
                currentPath = getPath(this, uri);
                localText.setText(currentPath);
                break;
            default:
                break;
        }
    }
    protected void onAdd(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent,OPENFILE_CODE);
    }
    protected String getPath(Context context, Uri uri){
        String path = null;
        if("content".equalsIgnoreCase(uri.getScheme())){
            String[] projection = {MediaStore.MediaColumns.DATA};
            String colum_name = "_data";
            Cursor cursor = null;
            try{
                cursor = context.getContentResolver().query(uri,projection,null,null,null);
                Log.w("XIAO","count =" +cursor.getCount());
                if(cursor != null && cursor.moveToFirst()){
                    int colum_index = cursor.getColumnIndex(colum_name);
                    path =  cursor.getString(colum_index);
                }
            }catch (Exception e){
                Log.w("XIAO",e.getMessage(),e);
            }finally {
                if(cursor != null){
                    cursor.close();
                }
            }
        }else if("file".equalsIgnoreCase(uri.getScheme())) {
            path =  uri.getPath();
        }else {
            Toast.makeText(this, "选择文件路径为空", Toast.LENGTH_SHORT).show();
        }
        return path;
    }

    public void upload() {
        if(TextUtils.isEmpty(currentPath)){
            Toast.makeText(FileUploadActivity.this,"请选择文件", Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                String filename = FileUtils.getFileName(currentPath);
                String cosPath =  "/" + filename; //cos 上的路径
                PutObjectSample.putObjectForSamllFile(bizService,cosPath,currentPath);
            }
        }).start();
    }
    public void upload2() {
        if(TextUtils.isEmpty(currentPath)){
            Toast.makeText(FileUploadActivity.this,"请选择文件", Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                String filename = FileUtils.getFileName(currentPath);
                String cosPath =  "/" + filename; //cos 上的路径
                PutObjectSample.putObjectForLargeFile(bizService,cosPath,currentPath);
            }
        }).start();
    }

    public void updateMeta(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                String cosPath = "/Screenshot_2017-11-01-11-15-44.png";
                UpdateObjectSample updateObjectSample = new UpdateObjectSample();
                updateObjectSample.updateObject(bizService, cosPath);

                GetObjeceMetadataSample getObjeceMetadataSample = new GetObjeceMetadataSample();
                getObjeceMetadataSample.getObjectMeta(bizService, cosPath);
            }
        }).start();


    }


}
