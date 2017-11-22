package com.tencent.qcloud.cosv4demo.utils;


/**
 * 签名抽象类，用户需要实现这个类将原始串转化为签名串
 */
public abstract class AbsCredentialProvider {

    protected String secretKey;


    public AbsCredentialProvider(String secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * 签名函数：将原始字符串进行加密，具体的加密算法见文档
     * @param source 原始串
     * @return 签名串
     */
    protected abstract String encrypt(String source);


}