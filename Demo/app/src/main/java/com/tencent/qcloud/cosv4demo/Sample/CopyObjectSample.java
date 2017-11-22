package com.tencent.qcloud.cosv4demo.Sample;

import android.util.Log;

import com.tencent.cos.model.COSRequest;
import com.tencent.cos.model.COSResult;
import com.tencent.cos.model.CopyObjectRequest;
import com.tencent.cos.task.listener.ICmdTaskListener;

/**
 * Created by bradyxiao on 2017/4/11.
 * author bradyxiao
 */
public class CopyObjectSample {
    public static void copyObject(BizService bizService,
                                  String cosPathSrc, String cosPathDest){
        CopyObjectRequest copyObjectRequest = new CopyObjectRequest();
        copyObjectRequest.setBucket(bizService.bucket);
        copyObjectRequest.setCosPath(cosPathSrc);
        copyObjectRequest.setDestFileId(cosPathDest);
        copyObjectRequest.setToOverWrite(1);
        copyObjectRequest.setSign(bizService.getSignOnce(cosPathSrc));
        copyObjectRequest.setListener(new ICmdTaskListener() {
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
        bizService.cosClient.copyObject(copyObjectRequest);
    }
}
