### 功能
这个controller的功能是用来管理用户登录登出信息的。

### 请求API
* 登录
```$xslt
请求方式： post
URL： http://ip:8081/classApp/tuser/login

参数：username    账号
     password    密码
     
参数传递方式： 表单方式

return： reponse头中有token， 然后就是注册成功的信息。
```

* 登出
```$xslt
请求方式： post
URL： http://ip:8081/classApp/tuser/logout

header: token

参数：无
   
参数传递方式： 无

return  ： 返回成功或者失败信息
```

* 获取当前信息用户
```$xslt
请求方式： post
URL： http://ip:8081/classApp/tuser/getInfo

header: token

参数：无
   
参数传递方式： 无

return  ： 返回成功或者失败信息
```
