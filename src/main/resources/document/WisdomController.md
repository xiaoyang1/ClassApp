### 功能
这个controller的功能是用来管理名言信息的。

### 请求API

* 添加一条新的名言
```
请求方式： post
URL： http://ip:8081/classApp/twisdom/addWisdom

header： token  （要带上token）
参数： wisdom  (String)  
      url   (String) 
     
参数传递方式： 表单方式请求

return： 成功信息
```

* 获得最新的名言
```$xslt
URL： http://ip:8081/classApp/twisdom/getNewest
请求方式：  post

header： token  （要带上token）

参数： 无
      
return  ： 成功： 返回名言， url
           失败：返回错误信息
```
