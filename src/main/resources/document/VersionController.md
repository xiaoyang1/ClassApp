### 功能
这个controller的功能是用来管理版本信息的。

### 请求API

* 获得版本信息
```
请求方式： post
URL： http://ip:8081/classApp/tversion/getLastVersion

参数：无
    请求中，带有token
    
return： json{"version": "1.1.0", "download": "下载地址"}
```
