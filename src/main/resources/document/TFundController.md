### 功能
这个controller的功能是用来管理用户资产收益信息的。

### 请求API

* 获得个人的投资信息
```
请求方式： post
URL： http://ip:8081/classApp/tfund/getInvestation
header： token

参数：无

return： 错误： code=0, 加上错误信息
         成功： code, data中返回一个json对象 ,userId, totalFund, earlyUpInvestTod，earlyUpInvestTom
         分别是用户的id， 总资产， 今日早起打卡投资，明日早起打卡投资
         
```

* 获得今日收益
```$xslt
URL： http://ip:8081/classApp/tfund/getIncome
请求方式：  post
header： token  

参数： 无
      
return  ： 错误： code=0, 加上错误信息
           成功：  成功信息为字符串，存在data中。
           字段： userId, totalFund, earlyUpIncome, sportIncome, income
                用户id    个人总资产    今日早起投资收益   运动收益    今日总收益 = 早起+运动
```

* 获得今日奖池的信息
```$xslt
URL： http://ip:8081/classApp/tfund/getBonusInfo
请求方式： post

header： token
参数： 无


return  ： 错误： code=0， 时间在0点到8点半，不能查询，打卡未停止
           成功： 返回 Json{ numOfPeopleToDevide：瓜分人数(打卡成功的人) ， totalBonus : 瓜分总资产 }
      
```

