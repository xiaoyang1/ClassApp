### 功能
这个controller的功能是用来管理用户课程表信息的。

### 请求API

* 新插入课程表信息
```
请求方式： post
URL： http://ip:8081/classApp/tclass/insertClass

参数：classSchedule: 课程表信息，，必须是严格的json格式
http： token
     
参数传递方式： 表单方式请求

return ： 错误： code = 0, 错误信息
          成功： code = 1， 内容为空

```

* 更新课表
```$xslt
URL： http://ip:8081/classApp/tclass/updateClass
请求方式：  post

参数：classSchedule: 课程表信息，，必须是严格的json格式
http： token
     
参数传递方式： 表单方式请求

return ： 错误： code = 0, 错误信息
          成功： code = 1， 内容为空
          
特别说明：后台实现时，如果客户不存在对应课程表数据库的信息，会直接新建一条数据，
        所以，新插入课程表的话可以直接调用这个api。
```

* 删除课表
```$xslt
URL： http://ip:8081/classApp/tclass/deleteClass
请求方式： post

参数： 无
header： token

说明：根据用户内部的id去删除

return  ： 返回成功或者失败信息
      
```

* 获取个人课表
```$xslt
URL： http://ip:8081/classApp/tclass/getClass
请求方式： post

参数： 无
header： token


return  ： 错误： code = 0， 错误信息
           成功： code = 1， json{"class": "课程表json"， "lastMod", 最后修改时间}
      
```

* 分享课表
```$xslt
URL： http://ip:8081/classApp/tclass/shareClass
请求方式： post

参数： 无
header： token


return  ： json{"shareKey", 共享码}, 有效时间为15分钟
      
```


* 获取分享课表
```$xslt
URL： http://ip:8081/classApp/tclass/getShareClass
请求方式： post

参数： shareKey
header： token
参数传递方式： 表单传递


return  ： 错误： code = 0， 错误信息
           成功： code = 1， 课程表信息
      
```
