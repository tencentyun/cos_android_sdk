package com.tencent.qcloud.cosv4demo.Sample;

import android.util.Log;

import com.tencent.cos.model.COSRequest;
import com.tencent.cos.model.COSResult;
import com.tencent.cos.model.DeleteObjectRequest;
import com.tencent.cos.task.listener.ICmdTaskListener;

/**
 * Created by bradyxiao on 2017/4/11.
 * author bradyxiao
 */
public class DeleteObjectSample {
    public static void deleteObject(BizService bizService,String cosPath){
        /** DeleteObjectRequest 请求对象 */
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest();
        /** 设置Bucket */
        deleteObjectRequest.setBucket(bizService.bucket);
        /** 设置cosPath :远程路径*/
        deleteObjectRequest.setCosPath(cosPath);
        /** 设置sign: 签名，此处使用单次签名 */
        deleteObjectRequest.setSign(bizService.getSignOnce(cosPath));
        /** 设置listener: 结果回调 */
        deleteObjectRequest.setListener(new ICmdTaskListener() {
            @Override
            public void onSuccess(COSRequest cosRequest, COSResult cosResult) {
                String result = "code =" + cosResult.code + "; msg =" + cosResult.msg;
                Log.w("XIAO",result);
            }

            @Override
            public void onFailed(COSRequest cosRequest, COSResult cosResult) {
                String result = "cancel =" + cosResult.msg;
                Log.w("XIAO",result);
            }
        });
        /** 发送请求：执行 */
       bizService.cosClient.deleteObject(deleteObjectRequest);
    }
}
