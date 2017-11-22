package com.tencent.qcloud.cosv4demo.Sample;

import android.text.TextUtils;
import android.util.Log;

import com.tencent.cos.model.COSRequest;
import com.tencent.cos.model.COSResult;
import com.tencent.cos.model.ListDirRequest;
import com.tencent.cos.model.ListDirResult;
import com.tencent.cos.task.listener.ICmdTaskListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bradyxiao on 2017/4/11.
 * author bradyxiao
 */
public class ListDirSample {
    public static void listDir(BizService bizService, String cosPath,String prefix){
        /** ListDirRequest 请求对象 */
        ListDirRequest listDirRequest = new ListDirRequest();
        /** 设置Bucket */
        listDirRequest.setBucket(bizService.bucket);
        /** 设置cosPath :远程路径*/
        listDirRequest.setCosPath(cosPath);
        /** 设置num :预查询的目录数*/
        listDirRequest.setNum(100);
        /** 设置content: 透传字段，首次拉取必须清空。拉取下一页，需要将前一页返回值中的context透传到参数中*/
        listDirRequest.setContent("");
        /** 设置sign: 签名，此处使用多次签名 */
        listDirRequest.setSign(bizService.getSign());
        /** 设置listener: 结果回调 */
        listDirRequest.setListener(new ICmdTaskListener() {
            @Override
            public void onSuccess(COSRequest cosRequest, COSResult cosResult) {
                ListDirResult listObjectResult = (ListDirResult) cosResult;
                //文件夹 =》不含有  filesize 或 sha 或 access_url 或 souce_url
                StringBuilder stringBuilder = new StringBuilder("目录列表查询结果如下：");
                stringBuilder.append("code =" + listObjectResult.code + "; msg =" + listObjectResult.msg + "\n");
                stringBuilder.append("list是否结束：").append(listObjectResult.listover).append("\n");
                stringBuilder.append("content = " + listObjectResult.context + "\n");
                if(listObjectResult.infos != null && listObjectResult.infos.size() > 0){
                    int length = listObjectResult.infos.size();
                    String str;
                    JSONObject jsonObject;
                    StringBuilder fileStringBuilder = new StringBuilder();
                    StringBuilder dirStringBuilder = new StringBuilder();
                    for(int i = 0; i < length; i++){
                        str = listObjectResult.infos.get(i);
                        try {
                            jsonObject = new JSONObject(str);
                            if(jsonObject.has("sha")){
                                //是文件
                                fileStringBuilder.append("文件：" + jsonObject.optString("name")).append("\n");
                            }else{
                                //是文件夹
                                dirStringBuilder.append("文件夹： " + jsonObject.optString("name")).append("\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    stringBuilder.append(fileStringBuilder);
                    stringBuilder.append(dirStringBuilder);
                }else{
                    stringBuilder.append("该目录下无内容");
                }
                String result = stringBuilder.toString();
                Log.w("XIAO",result);
            }

            @Override
            public void onFailed(COSRequest cosRequest, COSResult cosResult) {
                String result = "目录查询失败：ret=" + cosResult.code  + "; msg =" + cosResult.msg;
                Log.w("XIAO",result);
            }
        });
        /** 设置 prefix: 前缀查询的字符串,开启前缀查询 */
        if(!TextUtils.isEmpty(prefix)){
            listDirRequest.setPrefix(prefix);
        }
        /** 发送请求：执行 */
       bizService.cosClient.listDir(listDirRequest);
    }
}
