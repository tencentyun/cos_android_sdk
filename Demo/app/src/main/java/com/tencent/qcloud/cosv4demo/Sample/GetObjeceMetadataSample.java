package com.tencent.qcloud.cosv4demo.Sample;

import android.util.Log;

import com.tencent.cos.model.COSRequest;
import com.tencent.cos.model.COSResult;
import com.tencent.cos.model.GetObjectMetadataRequest;
import com.tencent.cos.model.GetObjectMetadataResult;
import com.tencent.cos.task.listener.ICmdTaskListener;

/**
 * Created by bradyxiao on 2017/4/11.
 * author bradyxiao
 */
public class GetObjeceMetadataSample {
    public static void getObjectMeta(BizService bizService,String cosPath){
        /** GetObjectMetadataRequest 请求对象 */
        GetObjectMetadataRequest getObjectMetadataRequest = new GetObjectMetadataRequest();
        /** 设置Bucket */
        getObjectMetadataRequest.setBucket(bizService.bucket);
        /** 设置cosPath :远程路径*/
        getObjectMetadataRequest.setCosPath(cosPath);
        /** 设置sign: 签名，此处使用多次签名 */
        getObjectMetadataRequest.setSign(bizService.getLocalSign());
        /** 设置listener: 结果回调 */
        getObjectMetadataRequest.setListener(new ICmdTaskListener() {
            @Override
            public void onSuccess(COSRequest cosRequest, COSResult cosResult) {
                GetObjectMetadataResult getObjectMetadataResult = (GetObjectMetadataResult) cosResult;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("查询结果 = ")
                        .append(" code = ").append(getObjectMetadataResult.code)
                        .append(", msg =").append(getObjectMetadataResult.msg)
                        .append(", ctime =").append(getObjectMetadataResult.mtime)
                        .append(", mtime =").append( getObjectMetadataResult.ctime).append("\n")
                        .append(", filesize =").append(getObjectMetadataResult.filesize)
                        .append(", filesize =").append(getObjectMetadataResult.filesize)
                        .append(", access_url =").append(getObjectMetadataResult.access_url == null?"null":getObjectMetadataResult.access_url)
                        .append(", sha =").append(getObjectMetadataResult.sha == null?"null":getObjectMetadataResult.sha)
                        .append(", authority =").append(getObjectMetadataResult.authority == null?"null":getObjectMetadataResult.authority)
                        .append(", biz_attr =").append(getObjectMetadataResult.biz_attr == null?"null":getObjectMetadataResult.biz_attr);
                String result = stringBuilder.toString();
                Log.w("XIAO",result);
            }

            @Override
            public void onFailed(COSRequest cosRequest, COSResult cosResult) {
                String result = "查询出错： ret =" + cosResult.code + "; msg =" + cosResult.msg
                        + "; requestId =" + cosResult.requestId;
                Log.w("XIAO",result);
            }
        });
        /** 发送请求：执行 */
       bizService.cosClient.getObjectMetadata(getObjectMetadataRequest);
    }
}
