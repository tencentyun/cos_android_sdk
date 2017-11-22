package com.tencent.qcloud.cosv4demo.Sample;

import android.util.Log;

import com.tencent.cos.model.COSRequest;
import com.tencent.cos.model.COSResult;
import com.tencent.cos.model.GetObjectRequest;
import com.tencent.cos.task.listener.IDownloadTaskListener;

/**
 * Created by bradyxiao on 2017/4/11.
 * author bradyxiao
 */
public class GetObjectSample {
    public static void getObject(BizService bizService, String url, String savePath){
        /** GetObjectRequest 请求对象 */
        GetObjectRequest getObjectRequest = new GetObjectRequest(url,savePath);
        //若是设置了防盗链则需要签名；否则，不需要
        /** 设置listener: 结果回调 */
        getObjectRequest.setListener(new IDownloadTaskListener() {
            @Override
            public void onProgress(COSRequest cosRequest, long currentSize, long totalSize) {
                long progress = (long) ((100.00 * currentSize) / totalSize);
                Log.w("XIAO","progress =" + progress + "%");
            }

            @Override
            public void onCancel(COSRequest cosRequest, COSResult cosResult) {
                String result = "cancel =" + cosResult.msg;
                Log.w("XIAO",result);
            }

            @Override
            public void onSuccess(COSRequest cosRequest, COSResult cosResult) {
                String result = "code =" + cosResult.code + "; msg =" + cosResult.msg;
                Log.w("XIAO",result);
            }

            @Override
            public void onFailed(COSRequest cosRequest, COSResult cosResult) {
                String result ="code =" + cosResult.code + "; msg =" + cosResult.msg;
                Log.w("XIAO",result);
            }
        });
        bizService.cosClient.getObject(getObjectRequest);
    }
}
