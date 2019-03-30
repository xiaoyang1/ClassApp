package cn.quantum.web.controller.activity;


import cn.quantum.annotation.BusinessLog;
import cn.quantum.util.ResultVOUtil;
import cn.quantum.web.service.TAuthService;
import cn.quantum.web.service.TSportService;
import cn.quantum.web.vo.ResultVO;
import cn.quantum.web.vo.TUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 这个类主要是用来完成运动打卡任务的
 */
@RestController
@RequestMapping("/tsport")
@BusinessLog("运动打卡管理")
@Slf4j
public class TSportController {

    @Autowired
    private TSportService tSportService;
    @Autowired
    private TAuthService tAuthService;


    @PostMapping("/sportToday")
    @BusinessLog("今日运动打卡")
    public ResultVO sportToday(HttpServletRequest request){
        TUserVO userVO = tAuthService.getUserInfo(request);
        String ifComplete = request.getParameter("status");
        try{
            int status = Integer.parseInt(ifComplete);
            if(status != 0){
                // 打卡成功
                return tSportService.sportToday(userVO, true);
            } else {
                // 打卡失败
                return tSportService.sportToday(userVO, false);
            }
        } catch (NumberFormatException e){
            return ResultVOUtil.error(0, "打卡状态异常!");
        }
    }

    /**
     * 该方法处理今天运动打卡申请
     * @param request
     * @return
     */
    @PostMapping("/subscribeToday")
    @BusinessLog("申请今天打卡")
    public ResultVO subscribeToday(HttpServletRequest request) {
        TUserVO userVO = tAuthService.getUserInfo(request);
        String ifToday = request.getParameter("status");
        try{
            int status = Integer.parseInt(ifToday);
            return tSportService.subscribeToday(userVO, status != 0 ? Boolean.TRUE : Boolean.FALSE);
        } catch (NumberFormatException e){
            return ResultVOUtil.error(0, "预约打卡状态异常!");
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
    public ResultVO getCount(HttpServletRequest request) {
        TUserVO userVO = tAuthService.getUserInfo(request);
        return tSportService.getCount(userVO);
    }

    /**
     * 该方法主要用来返回当前完成和申请状态
     * @param request
     * @return
     */
    @RequestMapping("/getSportStatus")
    @BusinessLog("获得用户当天的运动打卡和预约状态")
    public ResultVO getSportStatus(HttpServletRequest request) {
        TUserVO userVO = tAuthService.getUserInfo(request);
        return tSportService.getSportStatus(userVO);
    }

    @RequestMapping("/getSportInfo")
    @BusinessLog("获得个人的运动打卡挑战总数以及成功总数")
    public ResultVO getSportInfo(HttpServletRequest request){
        TUserVO userVO = tAuthService.getUserInfo(request);
        return tSportService.getSportInfo(userVO);
    }

}
