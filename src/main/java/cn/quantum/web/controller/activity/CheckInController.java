package cn.quantum.web.controller.activity;

import cn.quantum.annotation.BusinessLog;
import cn.quantum.util.CheckInUtil;
import cn.quantum.util.ResultVOUtil;
import cn.quantum.web.service.EarlyGetUpService;
import cn.quantum.web.service.TAuthService;
import cn.quantum.web.vo.ResultVO;
import cn.quantum.web.vo.TUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 这个类主要是用来完成打卡任务的
 */
@RestController
@RequestMapping("/tcheckin")
@BusinessLog("打卡管理")
@Slf4j
public class CheckInController {

    @Autowired
    private EarlyGetUpService earlyGetUpService;
    @Autowired
    private TAuthService tAuthService;

    @PostMapping("/checkInToday")
    @BusinessLog("今日打卡")
    public ResultVO checkInToday(HttpServletRequest request){
        TUserVO userVO = tAuthService.getUserInfo(request);
        String ifComplete = request.getParameter("status");

        // 判断是否在打卡时间内
        if(!CheckInUtil.canCheckIn){
            return ResultVOUtil.error(0, "目前不在打卡时间内，不能打卡！");
        }

        try{
            int status = Integer.parseInt(ifComplete);
            if(status != 0){
                // 打卡成功
                return earlyGetUpService.checkInToday(userVO, true);
            } else {
                // 打卡失败
                return earlyGetUpService.checkInToday(userVO, false);
            }
        } catch (NumberFormatException e){
            return ResultVOUtil.error(0, "打卡状态异常!");
        }
    }

    /**
     * 该方法处理明天打卡申请
     * @param request
     * @return
     */
    @PostMapping("/checkInTomorrow")
    @BusinessLog("申请明天打卡")
    public ResultVO checkInTomorrow(HttpServletRequest request){
        TUserVO userVO = tAuthService.getUserInfo(request);
        String ifTomorrow = request.getParameter("status");
        try{
            int status = Integer.parseInt(ifTomorrow);
            return earlyGetUpService.checkInTomorrow(userVO, status != 0 ? Boolean.TRUE : Boolean.FALSE);
        } catch (NumberFormatException e){
            return ResultVOUtil.error(0, "打卡状态异常!");
        }
    }

    /**
     *  该方法返回统计数据，包括当天参与挑战的人数，成功的人数，明天预订的人数
     *  目前先采取每次都查数据库的方式，后面尝试一下切片
     * @param request
     * @return {"today":1,"tomorrow":0,"complete":0}
     */
    @RequestMapping("/getCount")
    @BusinessLog("获得统计数据")
    public ResultVO getCount(HttpServletRequest request){
        TUserVO userVO = tAuthService.getUserInfo(request);
        return earlyGetUpService.getCount(userVO);
    }

    /**
     * 该方法主要用来返回当前完成和申请状态
     * @param request
     * @return
     */
    @RequestMapping("/getCheckInStatus")
    @BusinessLog("获得用户当天的早起打卡和预约状态")
    public ResultVO getCheckInStatus(HttpServletRequest request){
        TUserVO userVO = tAuthService.getUserInfo(request);
        return earlyGetUpService.getCheckInStatus(userVO);
    }

    @RequestMapping("/getCheckInInfo")
    @BusinessLog("获得个人的早起打卡挑战总数以及成功总数")
    public ResultVO getCheckInInfo(HttpServletRequest request){
        TUserVO userVO = tAuthService.getUserInfo(request);
        return earlyGetUpService.getCheckInInfo(userVO);
    }
}
