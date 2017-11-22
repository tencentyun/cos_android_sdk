package com.tencent.qcloud.cosv4demo.Sample;

import android.util.Log;

import com.tencent.cos.model.COSRequest;
import com.tencent.cos.model.COSResult;
import com.tencent.cos.model.MoveObjectRequest;
import com.tencent.cos.task.listener.ICmdTaskListener;

/**
 * Created by bradyxiao on 2017/4/11.
 * author bradyxiao
 */
public class MoveObjcetSample {
    public static void moveObject(BizService bizService, String cosPathsrc, String cosPathDest){
        /** MoveObjectRequest 请求对象 */
        MoveObjectRequest moveObjectRequest = new MoveObjectRequest();
        /** 设置Bucket */
        moveObjectRequest.setBucket(bizService.bucket);
        /** 设置cosPath :远程源文件路径*/
        moveObjectRequest.setCosPath(cosPathsrc);
        /** 设置dest_fileId :远程目标文件路径*/
        moveObjectRequest.setDestFileId(cosPathDest);
        /** 设置to_Over_Write :是否覆盖 0：不覆盖 1：覆盖*/
        moveObjectRequest.setToOverWrite(1);
        /** 设置sign: 签名，此处使用单次签名 */
        moveObjectRequest.setSign(bizService.getSignOnce(cosPathsrc));
        /** 设置listener: 结果回调 */
        moveObjectRequest.setListener(new ICmdTaskListener() {
            @Override
            public void onSuccess(COSRequest cosRequest, COSResult cosResult) {
                String result = "code =" + cosResult.code + "; msg =" + cosResult.msg;
                Log.w("XIAO",result);
            }

            @Override
            public void onFailed(COSRequest COSRequest, COSResult cosResult) {
                String result = "code =" + cosResult.code + "; msg =" + cosResult.msg;
                Log.w("XIAO",result);
            }
        });
       bizService.cosClient.moveObjcet(moveObjectRequest);
    }
}
