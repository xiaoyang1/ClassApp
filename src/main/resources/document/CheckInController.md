### 功能
这个controller的功能是用来管理用户打卡信息的。

### 请求API

* 今日打卡
```
请求方式： post
URL： http://ip:8081/classApp/tcheckin/checkInToday
header： token
参数：status 打卡状态， 0 代表 打卡失败， 1代表成功
     
参数传递方式： 表单方式请求

return： 错误： code=0, 加上错误信息
         成功： code, data中返回一个json对象 ,具体的字段自己请求看一下
         字段说明： ifDo是否已经打卡
         result:
         {
            "today": {"ifToday":1,"ifTomorrow":0,"ifComplete":0, "ifDo":1},
            "total": {也是json对象}
         }
```

* 申请明日打卡
```$xslt
URL： http://ip:8081/classApp/tcheckin/checkInTomorrow
请求方式：  post
header： token  

参数： status 打卡状态， 0 代表 不申请， 1代表申请 （主要为了灵活，可以取消申请状态）
参数传递方式：表单方式
      
return  ： 错误： code=0, 加上错误信息
           成功：  成功信息为字符串，存在data中。
```

* 获得参与打卡计划的所有成功和失败数目
```$xslt
URL： http://ip:8081/classApp/tcheckin/getCount
请求方式： post

header： token
参数： 无


return  ： {"today":1, "tomorrow":0, "complete":0, "failed":1}
      
```

* 获取个人当天的早起打卡和预约状态
```$xslt
URL： http://ip:8081/classApp/tcheckin/getCheckInStatus
请求方式： post

header： token
参数： 无


return  ：data: {"ifToday":1,"ifTomorrow":0,"ifComplete":0, "ifDo":1}
      
```



* 获取个人早起打卡总记录
```$xslt
URL： http://ip:8081/classApp/tcheckin/getCheckInInfo
请求方式： post

header： token
参数： 无


return  ：data: {"total":1,"success":0,"failed":0}
      
```