# cos_android_sdk 导入的第一种方式：
cos_android_sdk

配置工程导入下列 jar 包：

- cos-android-sdk1.4.3.18.jar
- okhttp-3.2.0.jar
- okio-1.6.0.jar
- sha1utils.jar
- jniLibs(对应的sha1值计算的.so库)

更多示例可参考Demo


# cos_android_sdk 导入的第二种方式：

在build.gradle中，添加如下依赖:

```
dependencies {
    compile 'com.tencent.cos:cos:4.3.2+@aar'
    
    compile 'com.squareup.okhttp3:okhttp:3.2.0'
}
``` 

# 注意： 

# 请求返回的两个字段：access_url 和 source_url,其中source_url是默认的下载地址，且已经过URLEncode; access_url是cdn加速下载地址，未经过URLEncode.

目录创建操作：

```
CreateDirResult createDir(CreateDirRequest createDirRequest); //同步方法

void createDirAsyn(CreateDirRequest createDirRequest); // 异步方法
```

空目录删除操作

```
RemoveEmptyDirResult removeEmptyDir(RemoveEmptyDirRequest removeEmptyDirRequest); //同步方法

void removeEmptyDirAsyn(RemoveEmptyDirRequest removeEmptyDirRequest);  // 异步方法
```

目录列表查询操作
```
ListDirResult listDir(ListDirRequest listDirRequest); //同步方法
 
void listDirAsyn(ListDirRequest listDirRequest);  // 异步方法
```
上传操作
```
PutObjectResult putObject(PutObjectRequest putObjectRequest); //同步方法

void putObjectAsyn(PutObjectRequest putObjectRequest);  // 异步方法
```
查询文件操作
```
GetObjectMetadataResult getObjectMetadata(GetObjectMetadataRequest getObjectMetadataRequest);
//同步方法

void getObjectMetadataAsyn(GetObjectMetadataRequest getObjectMetadataRequest);  // 异步方法
```
更新操作：
```
UpdateObjectResult updateObject(UpdateObjectRequest updateObjectRequest); //同步方法

void updateObjectAsyn(UpdateObjectRequest updateObjectRequest);  // 异步方法
```
删除操作：
```
DeleteObjectResult deleteObject(DeleteObjectRequest deleteObjectRequest); //同步方法

void deleteObjectAsyn(DeleteObjectRequest deleteObjectRequest);  // 异步方法
```
查询bucket操作：
```
HeadBucketResult headBucket(HeadBucketRequest headBucketRequest); //同步方法

void headBucketAsyn(HeadBucketRequest headBucketRequest); // 异步方法
```
下载操作：
```
GetObjectResult getObject(GetObjectRequest getObjectRequest); //同步方法

void getObjectAsyn(GetObjectRequest getObjectRequest); // 异步方法
```
Move操作：
```
MoveObjectResult moveObjcet(MoveObjectRequest moveObjectRequest); //同步方法

void moveObjcetAsyn(MoveObjectRequest moveObjectRequest);  // 异步方法
```
Copy操作：
```
CopyObjectResult copyObject(CopyObjectRequest copyObjectRequest); //同步方法

void copyObjectAsyn(CopyObjectRequest copyObjectRequest);  // 异步方法
```
