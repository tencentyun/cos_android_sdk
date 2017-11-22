package com.tencent.qcloud.cosv4demo.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 本地签名类
 *
 *    用户可以使用这个类本地生成签名，但主要用于测试，为了安全性，正式版本请在第三方服务器上获取签名
 */
public class LocalCredentialProvider extends AbsCredentialProvider{

    public LocalCredentialProvider(String secretKey) {
        super(secretKey);
    }

    @Override
    protected String encrypt(String source) {
        String sign = "";
        byte[] hmacSha1 = hmacSha1(source, secretKey);
        if (hmacSha1==null) {
            return "";
        }
        try {
            byte[] urlByte = source.getBytes("utf-8");
            byte[] signByte = new byte[hmacSha1.length+urlByte.length];
            System.arraycopy(hmacSha1, 0, signByte, 0, hmacSha1.length);
            System.arraycopy(urlByte, 0, signByte, hmacSha1.length, urlByte.length);
            sign = Base64.encodeToString(signByte, Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sign = sign.replaceAll("\n", "");
        return sign;
    }

    private byte[] hmacSha1(String url, String secreteKey) {

        byte[] hmacSha1 = null;
        try {
            byte[] byteKey = secreteKey.getBytes("utf-8");
            SecretKey hmacKey = new SecretKeySpec(byteKey, "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(hmacKey);
            hmacSha1 = mac.doFinal(url.getBytes("utf-8"));

        } catch (UnsupportedEncodingException e) {

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return hmacSha1;
    }

    /**
     *
     * @param appid
     * @param bucket
     * @param secretId
     * @param fileId
     * @param expiredTimes: expired time(s).
     * @return
     */
    public String getSource(String appid, String bucket, String secretId,
                            String fileId, long expiredTimes){
        StringBuilder source = new StringBuilder();
        /**
         * 形如：1) a=[appid]&b=[bucket]&k=[SecretID]&e=[expiredTime]&t=[currentTime]&r=[rand]&f=；
         *       2) a=[appid]&b=[bucket]&k=[SecretID]&e=[expiredTime]&t=[currentTime]&r=[rand]&f=[fileid]；
         *       3) 其中检测签名时与各个字段的排序顺序无关；
         *
         * 签名类型：
         *        1）下载（不开启token防盗链）	不验证签名
         *        2） 上传	多次有效签名
         3）查询目录、文件	多次有效签名
         4）创建目录	多次有效签名
         5）下载（开启token防盗链）	多次有效签名
         6）删除目录、文件	单次有效签名
         7）更新目录、文件	单次有效签名
         */
        source.append("a=" + appid);
        source.append("&b=" + bucket);
        source.append("&k=" + secretId);
        long random = Math.abs(new Random().nextLong());
        source.append("&r=" + random);
        long t = System.currentTimeMillis()/1000;
        if(fileId == null){
            expiredTimes = t + expiredTimes;
            source.append("&e=" + expiredTimes);
            source.append("&t=" + t);
            source.append("&f=");
        }else {
            source.append("&e=" + 0);
            source.append("&t=" + t);
            fileId = "/" + appid + "/" + bucket + fileId;
            fileId = urlEncoder(fileId);
            source.append("&f=" + fileId);
        }
        return source.toString();
    }

    /**
     *对fileID进行URLEncoder编码
     */
    private String  urlEncoder(String fileID){
        if(fileID == null){
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        String[] strFiled = fileID.trim().split("/");
        int length = strFiled.length;
        for(int i = 0; i< length; i++){
            if("".equals(strFiled[i]))continue;
            stringBuilder.append("/");
            try{
                String str = URLEncoder.encode(strFiled[i], "utf-8").replace("+","%20");
                stringBuilder.append(str);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(fileID.endsWith("/")) stringBuilder.append("/");
        fileID = stringBuilder.toString();
        return fileID;
    }

    public String getSign(String appid, String bucket, String secretId,
                          String fileId, long expiredTimes){
        return encrypt(getSource(appid, bucket, secretId, fileId, expiredTimes));
    }

}