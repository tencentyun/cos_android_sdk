package com.tencent.qcloud.cosv4demo.Sample;

import android.util.Log;

import com.tencent.cos.model.COSRequest;
import com.tencent.cos.model.COSResult;
import com.tencent.cos.model.RemoveEmptyDirRequest;
import com.tencent.cos.task.listener.ICmdTaskListener;

/**
 * Created by bradyxiao on 2017/4/11.
 * author bradyxiao
 */
public class RemoveEmptyDirSample {
    public static void removeEmptyDir(BizService bizService, String cosPath){
        /** RemoveEmptyDirRequest 请求对象，只能删除空文件夹，其他无效 */
        RemoveEmptyDirRequest removeEmptyDirRequest = new RemoveEmptyDirRequest();
        /** 设置Bucket */
        removeEmptyDirRequest.setBucket(bizService.bucket);
        /** 设置cosPath :远程路径*/
        removeEmptyDirRequest.setCosPath(cosPath);
        /** 设置sign: 签名，此处使用单次签名 */
        removeEmptyDirRequest.setSign(bizService.getSignOnce(cosPath));
        /** 设置listener: 结果回调 */
        removeEmptyDirRequest.setListener(new ICmdTaskListener() {
            @Override
            public void onSuccess(COSRequest cosRequest, COSResult cosResult) {
                String result = "code =" + cosResult.code + "; msg =" + cosResult.msg;
                Log.w("XIAO",result);
            }

            @Override
            public void onFailed(COSRequest cosRequest, COSResult cosResult) {
                String result = "code =" + cosResult.code + "; msg =" + cosResult.msg;
                Log.w("XIAO",result);
            }
        });
        /** 发送请求：执行 */
       bizService.cosClient.removeEmptyDir(removeEmptyDirRequest);
    }
}
