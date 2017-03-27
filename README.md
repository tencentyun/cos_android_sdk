# cos_android_sdk 导入的第一种方式：
cos_android_sdk

配置工程导入下列 jar 包：

- cos-android-sdk1.4.3.jar
- okhttp-3.2.0.jar
- okio-1.6.0.jar
- sha1utils.jar
- jniLibs(对应的sha1值计算的.so库)

更多示例可参考Demo

2017年2月22日22 == 更新版本为： cos-android-sdk1.4.3.jar

# cos_android_sdk 导入的第二种方式：

在build.gradle中，添加如下依赖:

dependencies {
    compile 'com.tencent.cos:cos:4.3.2+@aar'
    
    compile 'com.squareup.okhttp3:okhttp:3.2.0'
}
   
