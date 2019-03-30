### 功能
这个controller的功能是用来管理邮件获取密码，获取验证码，绑定等信息的。

### 请求API

* 获得 验证码
```
请求方式： post
URL： http://ip:8081/classApp/temail/getValidCode

参数：emailAddress： 邮箱地址

    
return： 失败： code=0， 错误信息
        成功： code=1， validCode验证码信息， 并且发送邮箱到客户指定的邮箱
```

* 绑定邮箱
```
请求方式： post
URL： http://ip:8081/classApp/temail/bindEmail

参数：emailAddress： 邮箱地址
     validCode: 验证码
http： token

参数方式： 表单传递


return： 失败： code=0， 错误信息
        成功： code=1， 无信息，但是会发邮件到用户指定的邮箱
```

* 解除绑定邮箱
```
请求方式： post
URL： http://ip:8081/classApp/temail/unbind

参数：emailAddress： 邮箱地址
     validCode: 验证码

http： token

参数方式： 表单传递


return： 失败： code=0， 错误信息
        成功： code=1， 无信息，但是会发邮件到用户指定的邮箱
```

* 获取绑定邮箱
```
请求方式： post
URL： http://ip:8081/classApp/temail/getBindEmail

参数：无

http： token


return： 失败： code=0， 错误信息
        成功： code=1， 返回用户绑定邮箱
```


* 找回密码
```
请求方式： post
URL： http://ip:8081/classApp/temail/getPasswd

参数：emailAddress： 邮箱地址
     validCode: 验证码
     username : 账号


参数方式： 表单传递

return： 失败： code=0， 错误信息
        成功： code=1， 无信息，但是会把密码发邮件到用户指定的邮箱
```





