package cn.quantum.web.service.impl;

import cn.quantum.constant.InvestRule;
import cn.quantum.util.ResultVOUtil;
import cn.quantum.web.db.dao.TSportDao;
import cn.quantum.web.db.domain.CheckInInfo;
import cn.quantum.web.db.domain.TSport;
import cn.quantum.web.service.CheckInInfoService;
import cn.quantum.web.service.TFundService;
import cn.quantum.web.service.TSportService;
import cn.quantum.web.vo.ResultVO;
import cn.quantum.web.vo.TUserVO;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class TSportServiceImpl implements TSportService{

    @Autowired
    private TSportDao tSportDao;

    @Autowired
    private CheckInInfoService checkInInfoService;

    @Autowired
    private TFundService tFundService;

    @Override
    @Transactional
    public ResultVO  subscribeToday(TUserVO userVO, boolean b) {
        TSport tSport = tSportDao.getSportInfoByUserId(userVO.getId());
        if(tSport == null){
            tSport = new TSport(userVO.getId());
            tSportDao.insertToday(tSport);
        }
        if(tSport.getIfToday() == 1){
            // 说明已经申请打卡
            return ResultVOUtil.error(0, "今日已经申请打卡，请勿重复申请");
        }

        // 更新今日预约
        tSport.setIfToday(b ? 1 : 0);
        tSportDao.updateToday(tSport);

        // 更新个人总记录
        CheckInInfo selfInfo = checkInInfoService.findInfoById(userVO.getId());
        if(selfInfo != null){
            // 报名次数加一
            selfInfo.setCountSport(selfInfo.getCountSport() + 1);
            checkInInfoService.updateSportInfo(selfInfo);
        } else {
            selfInfo = new CheckInInfo();
            selfInfo.setUserId(userVO.getId());
            selfInfo.setCountSport(1);
            checkInInfoService.insertSportOne(selfInfo);
        }

        log.info("账号为：" + userVO.getUsername() + "  申请今天运动打卡计划！");
        return ResultVOUtil.success("已设置今天运动打卡状态!");
    }

    @Override
    public ResultVO getCount(TUserVO userVO) {
        Map<String, Object> allCount = tSportDao.getCount();
        if(allCount != null) {
            allCount.put("failed", Integer.parseInt(allCount.get("today").toString()) - Integer.parseInt(allCount.get("complete").toString()));
        } else {
            // 还没有记录
            allCount = new HashMap<String, Object>();
            allCount.put("today", 0);
            allCount.put("complete", 0);
            allCount.put("failed", 0);
        }
        JSONObject result = new JSONObject(allCount);
        System.out.println(result);
        return ResultVOUtil.success(result);
    }

    @Override
    public ResultVO getSportStatus(TUserVO userVO) {
        TSport status = tSportDao.getSportInfoByUserId(userVO.getId());
        if(status == null){
            status = new TSport(userVO.getId());
        }
        log.info("获取信息的状态为: " + status);
        return ResultVOUtil.success(status);
    }

    @Override
    @Transactional
    public ResultVO sportToday(TUserVO userVO, boolean b) {
        TSport status = tSportDao.getSportInfoByUserId(userVO.getId());

        if(status == null){
            return ResultVOUtil.error(0, "用户不存在打卡计划，无法打卡！");
        }
        if(status.getIfToday() == 0){
            return ResultVOUtil.error(0, "今日不存在打卡计划，无法打卡！");
        }
        if(status.getIfDo() == 1){
            if(status.getIfComplete() == 1){
                return ResultVOUtil.error(0, "已经成功打卡，今日内请勿重复打卡！");
            } else {
                return ResultVOUtil.error(0, "打卡异常，今日内无法再打卡！");
            }
        }

        // 更新今日打卡信息
        status.setIfDo(1);
        status.setIfComplete(b ? 1 : 0);
        tSportDao.updateCompleteByUserId(status);

        // 更新个人总记录
        CheckInInfo selfInfo = checkInInfoService.findInfoById(userVO.getId());
        if(selfInfo != null){
            if(status.getIfComplete() == 1){
                selfInfo.setSuccessSport(selfInfo.getSuccessSport() + 1);
                checkInInfoService.updateSportInfo(selfInfo);
            }
        }

        // 更新个人收入
        tFundService.sportIncome(userVO.getId(), InvestRule.sportIncome);

        JSONObject result = new JSONObject();
        result.put("today", status);
        result.put("total", selfInfo);
        log.info("update result : " + result);

        return ResultVOUtil.success(result);

    }

    @Override
    public ResultVO getSportInfo(TUserVO userVO) {
        CheckInInfo selfInfo = checkInInfoService.findInfoById(userVO.getId());
        JSONObject result = new JSONObject();

        result.put("total", selfInfo != null ? selfInfo.getCountSport() : 0);
        result.put("success", selfInfo != null ? selfInfo.getSuccessSport() : 0);
        result.put("failed", selfInfo != null ? selfInfo.getCountSport()-selfInfo.getSuccessSport() : 0);

        log.info("id = " + userVO.getId() + " 的用户获取了个人打卡总记录！ " + result);
        return ResultVOUtil.success(result);
    }
}
