package com.tencent.qcloud.cosv4demo.Sample;

import android.util.Log;

import com.tencent.cos.common.COSAuthority;
import com.tencent.cos.model.COSRequest;
import com.tencent.cos.model.COSResult;
import com.tencent.cos.model.UpdateObjectRequest;
import com.tencent.cos.task.listener.ICmdTaskListener;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by bradyxiao on 2017/4/11.
 * author bradyxiao
 */
public class UpdateObjectSample {
    public static void updateObject(BizService bizService, String cosPath){
        Map<String, String> customer = new LinkedHashMap<String, String>();
        customer.put("Cache-Control","no-cache");
        customer.put("Content-Disposition","attachment");
        customer.put("Content-Language","content=\"zh-cn\"");
        customer.put("x-cos-meta-","自定义属性");
        String biz_attr = "biz_attr";
        String authority = COSAuthority.EINVALID;
        /** UpdateObjectRequest 请求对象 */
        UpdateObjectRequest updateObjectRequest = new UpdateObjectRequest();
        /** 设置Bucket */
        updateObjectRequest.setBucket(bizService.bucket);
        /** 设置cosPath :远程路径*/
        updateObjectRequest.setCosPath(cosPath);
        /** 设置sign: 签名，此处使用单次签名 */
        updateObjectRequest.setSign(bizService.getLocalOnceSign(cosPath));
        /** biz_attr: 更新属性 */
        updateObjectRequest.setBizAttr(biz_attr);
        /** biz_attr: 更新文件头部属性*/
        updateObjectRequest.setCustomHeader(customer);
        /** biz_attr: 更新文件权限属性 */
        updateObjectRequest.setAuthority(authority);
        /** 设置listener: 结果回调 */
        updateObjectRequest.setListener(new ICmdTaskListener() {
            @Override
            public void onSuccess(COSRequest cosRequest, COSResult cosResult) {
                String result = cosResult.code+" : "+cosResult.msg;
                Log.w("XIAO",result);
            }

            @Override
            public void onFailed(COSRequest cosRequest, COSResult cosResult) {
                String result = cosResult.code+" : "+cosResult.msg;
                Log.w("XIAO",result);
            }
        });
        /** 发送请求：执行 */
     bizService.cosClient.updateObject(updateObjectRequest);
    }
}
