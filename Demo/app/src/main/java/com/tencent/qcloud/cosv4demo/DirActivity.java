package com.tencent.qcloud.cosv4demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tencent.qcloud.cosv4demo.Sample.BizService;
import com.tencent.qcloud.cosv4demo.Sample.CreateDirSample;
import com.tencent.qcloud.cosv4demo.Sample.GetObjeceMetadataSample;
import com.tencent.qcloud.cosv4demo.Sample.ListDirSample;
import com.tencent.qcloud.cosv4demo.Sample.RemoveEmptyDirSample;


public class DirActivity extends AppCompatActivity implements View.OnClickListener{
    Button createDir, deleteDir, statDir, listDir;
    BizService bizService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dir);
        createDir = (Button)findViewById(R.id.create);
        deleteDir = (Button)findViewById(R.id.delete);
        statDir = (Button)findViewById(R.id.stat);
        listDir = (Button)findViewById(R.id.listDir);

        createDir.setOnClickListener(this);
        deleteDir.setOnClickListener(this);
        statDir.setOnClickListener(this);
        listDir.setOnClickListener(this);

        bizService = BizService.instance();
        bizService.init(getApplicationContext());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.create:
                createDir();
                break;
            case R.id.delete:
                deleteDir();
                break;
            case R.id.stat:
                statDir();
                break;
            case R.id.listDir:
                listDir();
                break;
        }
    }
    /**
     * 1)创建书写合法目录 dirName = test;
     * 2)创建书写不合法目录 dirName = test>
     * 3)创建长度非法的目录  dirName = 1234567890122345678901; dirName.length()>20
     * 4)创建子目录下的目录 dirName = test/test
     * 5)创建多级的目录 dirName = test/test/test/test/test
     * 6)创建已存在的目录 dirName = test
     * 7)创建已删除的目录
     */
    public void createDir(){
        final String dirName = "test/";
        new Thread(new Runnable() {
            @Override
            public void run() {
                CreateDirSample.crateDir(bizService,dirName);
            }
        }).start();
    }

    public void deleteDir(){
        final String dirName = "test/";
        new Thread(new Runnable() {
            @Override
            public void run() {
                RemoveEmptyDirSample.removeEmptyDir(bizService,dirName);
            }
        }).start();
    }

    public void statDir(){
        final String dirName = "test/";
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetObjeceMetadataSample.getObjectMeta(bizService,dirName);
            }
        }).start();
    }

    /**
     * 1)bucket根目录自动展示   cosPath = "/"
     * 2）指定目录下的目录列表展示  cosPath = "test/"
     * 3）指定目录下存在文件和目录的展示   cosPath = "test/"
     * 4）指定目录下无目录和文件的展示     cosPath = "test/"
     * 5）单级木录下 或多级目录下的展示    cosPath = "test/"
     * 6）目录前缀查询，存在的关键子       cosPath = "test/" ;  listDirRequest.setPrefix("t");
     * 7）目录前缀查询，不存在的关键字     cosPath = "test/" ;  listDirRequest.setPrefix("t");
     * 8）目录前缀查询，单级和多级
     * 9）目录前缀查询，只存在子目录或只存在子文件或都存在   cosPath = "test/" ;  listDirRequest.setPrefix("t");
     * 10）设置显示数值，1, 10, 100
     */
    public void listDir(){
        final String dirName = "/";
        final String prefix = "2";
        new Thread(new Runnable() {
            @Override
            public void run() {
                ListDirSample.listDir(bizService,dirName,prefix);
            }
        }).start();
    }

}
