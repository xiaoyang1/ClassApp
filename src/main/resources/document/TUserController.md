### 功能
这个controller的功能是用来管理用户信息的。

### 请求API

* 注册用户
```
请求方式： post
URL： http://ip:8081/classApp/tuser/register

参数：username    账号
     password    密码
     entname     昵称
     
参数传递方式： json方式请求

return： reponse头中有token， 然后就是注册成功的信息。
```

* 修改密码
```$xslt
URL： http://ip:8081/classApp/tuser/updatePassword
请求方式：  post

header： token  （要带上token）

参数： oldPassword  原来密码
      newPassword  新密码
参数传递方式：表单方式
      
return  ： 返回成功或者失败信息
```

* 修改用户昵称
```$xslt
URL： http://ip:8081/classApp/tuser/modifyUserAntName
请求方式： post

header： token
参数： entName  新的昵称
参数传递方式： 表单传递

return  ： 返回成功或者失败信息
      
```