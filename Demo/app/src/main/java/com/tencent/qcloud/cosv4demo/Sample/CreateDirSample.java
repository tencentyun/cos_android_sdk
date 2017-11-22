package com.tencent.qcloud.cosv4demo.Sample;

import android.util.Log;

import com.tencent.cos.model.COSRequest;
import com.tencent.cos.model.COSResult;
import com.tencent.cos.model.CreateDirRequest;
import com.tencent.cos.model.CreateDirResult;
import com.tencent.cos.task.listener.ICmdTaskListener;

/**
 * Created by bradyxiao on 2017/4/11.
 * author bradyxiao
 */
public class CreateDirSample {
    public static void crateDir(BizService bizService,String cosPath){
        /** CreateDirRequest 请求对象 */
        CreateDirRequest createDirRequest = new CreateDirRequest();
        /** 设置Bucket */
        createDirRequest.setBucket(bizService.bucket);
        /** 设置cosPath :远程路径*/
        createDirRequest.setCosPath(cosPath);
        /** 设置biz_attr（一般不需要设置） :文件夹属性*/
        String biz_attr = "biz_attr";
        createDirRequest.setBizAttr(biz_attr);
        /** 设置sign: 签名，此处使用多次签名 */
        createDirRequest.setSign(bizService.getLocalSign());
        /** 设置listener: 结果回调 */
        createDirRequest.setListener(new ICmdTaskListener() {
            @Override
            public void onSuccess(COSRequest cosRequest, COSResult cosResult) {
                CreateDirResult createDirResult = (CreateDirResult) cosResult;
                String result = "目录创建： ret=" + createDirResult.code + "; msg=" + createDirResult.msg
                        + "ctime = " + createDirResult.ctime;
                Log.w("XIAO",result);
            }

            @Override
            public void onFailed(COSRequest cosRequest, COSResult cosResult) {
                String result =  "目录创建失败：ret=" + cosResult.code  + "; msg =" + cosResult.msg
                        + "; requestId =" + cosResult.requestId;
                Log.w("XIAO",result);
            }
        });
        /** 发送请求：执行 */
       bizService.cosClient.createDir(createDirRequest);
    }
}
