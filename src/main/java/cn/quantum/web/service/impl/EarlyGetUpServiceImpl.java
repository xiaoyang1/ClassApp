package cn.quantum.web.service.impl;

import cn.quantum.constant.InvestRule;
import cn.quantum.util.ResultVOUtil;
import cn.quantum.web.db.dao.EarlyGetUpDao;
import cn.quantum.web.db.dao.TFundDao;
import cn.quantum.web.db.domain.CheckInInfo;
import cn.quantum.web.db.domain.EarlyGetUp;
import cn.quantum.web.db.domain.Fund;
import cn.quantum.web.service.CheckInInfoService;
import cn.quantum.web.service.EarlyGetUpService;
import cn.quantum.web.service.TFundService;
import cn.quantum.web.vo.ResultVO;
import cn.quantum.web.vo.TUserVO;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class EarlyGetUpServiceImpl implements EarlyGetUpService {

    @Autowired
    private EarlyGetUpDao earlyGetUpDao;
    @Autowired
    private CheckInInfoService checkInInfoService;
    @Autowired
    private TFundService tFundService;
    @Autowired
    private TFundDao tFundDao;

    @Override
    @Transactional
    public ResultVO checkInTomorrow(TUserVO userVO, boolean b) {

        EarlyGetUp earlyGetUp = earlyGetUpDao.getEarlyInfoByUserId(userVO.getId());
        if(earlyGetUp != null){// 用户已经有过挑战记录
            // 已经有打卡记录的话
            if(earlyGetUp.getIfTomorrow() == 1){
                return ResultVOUtil.error(0, "已经申请了明天早起打卡，请勿重复申请！");
            }
            earlyGetUp.setIfTomorrow(b ? 1 : 0);
            earlyGetUpDao.updateTomorrowByUserId(earlyGetUp);
        }else {
            // 用户无打卡记录，新建一个
            earlyGetUp = new EarlyGetUp(userVO.getId());
            earlyGetUp.setIfTomorrow(b ? 1 : 0);
            earlyGetUpDao.insertTomorrow(earlyGetUp);
        }

        // 更新个人总记录
        CheckInInfo selfInfo = checkInInfoService.findInfoById(userVO.getId());
        if(selfInfo != null){
            // 报名次数加一
            selfInfo.setCountEarlyGetUp(selfInfo.getCountEarlyGetUp() + 1);
            checkInInfoService.updateEarlyUpInfo(selfInfo);
        } else {
            selfInfo = new CheckInInfo();
            selfInfo.setUserId(userVO.getId());
            selfInfo.setCountEarlyGetUp(1);
            checkInInfoService.insertEarlyUpOne(selfInfo);
        }

        // 注入投资金额
        Fund fund = tFundDao.getInvestation(userVO.getId());
        if(fund == null){
            fund = new Fund(userVO.getId());
            tFundService.createFundForUser(fund.getUserId());
        }
        fund.setEarlyUpInvestTom(InvestRule.earlyUpInvest);
        tFundService.updateEarlyUpInvestTom(fund);

        log.info("账号为：" + userVO.getUsername() + "  申请明天打卡计划！");
        return ResultVOUtil.success("已设置明天打卡状态!");
    }

    @Override
    public ResultVO getCount(TUserVO userVO) {
        Map<String, Object> allCount = earlyGetUpDao.getCount();
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
    public ResultVO getCheckInStatus(TUserVO userVO) {
        EarlyGetUp status = earlyGetUpDao.getEarlyInfoByUserId(userVO.getId());
        if(status == null) {
            status = new EarlyGetUp(userVO.getId());
        }
        log.info("获取信息的状态为: " + status);
        return ResultVOUtil.success(status);
    }

    @Override
    @Transactional
    public ResultVO checkInToday(TUserVO userVO, boolean b) {
        EarlyGetUp status = earlyGetUpDao.getEarlyInfoByUserId(userVO.getId());
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
        earlyGetUpDao.updateCompleteByUserId(status);

        // 更新个人总记录
        CheckInInfo selfInfo = checkInInfoService.findInfoById(userVO.getId());

        if(selfInfo != null) {
            if (status.getIfComplete() == 1) {
                selfInfo.setSuccessEarlyGetUp(selfInfo.getSuccessEarlyGetUp() + 1);
                checkInInfoService.updateEarlyUpInfo(selfInfo);
            }
        }


        JSONObject result = new JSONObject();
        result.put("today", status);
        result.put("total", selfInfo);
        log.info("update early up  result : " + result);

        return ResultVOUtil.success(result);
    }

    @Override
    public ResultVO getCheckInInfo(TUserVO userVO) {
        CheckInInfo selfInfo = checkInInfoService.findInfoById(userVO.getId());
        JSONObject result = new JSONObject();

        result.put("total", selfInfo != null ? selfInfo.getCountEarlyGetUp() : 0);
        result.put("success", selfInfo != null ? selfInfo.getSuccessEarlyGetUp() : 0);
        result.put("failed", selfInfo != null ? selfInfo.getCountEarlyGetUp()-selfInfo.getSuccessEarlyGetUp() : 0);

        log.info("id = " + userVO.getId() + " 的用户获取了个人早起打卡总记录！ " + result);
        return ResultVOUtil.success(result);
    }

    @Override
    public Set<Integer> getSuccessIds() {
        return earlyGetUpDao.getSuccessIds();
    }

    @Override
    public Set<Integer> getFailedIds() {
        return earlyGetUpDao.getFailedIds();
    }


}
