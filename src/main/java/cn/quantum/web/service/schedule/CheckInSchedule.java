package cn.quantum.web.service.schedule;

import cn.quantum.util.BonusPoolUtil;
import cn.quantum.util.CheckInUtil;
import cn.quantum.web.db.domain.Fund;
import cn.quantum.web.db.mapper.TFundMapper;
import cn.quantum.web.service.EarlyGetUpService;
import cn.quantum.web.service.TFundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 这个类用来更新打卡的允许时间段状态，
 */
@Service
@Slf4j
public class CheckInSchedule {
    @Autowired
    private EarlyGetUpService earlyGetUpService;
    @Autowired
    private TFundService tFundService;

    @Scheduled(cron = "0 30 08 ? * *")
    @Transactional
    // 每天早上8点半以后不允许打卡
    public void canNotEarlyGetUpCheckIn(){
        CheckInUtil.canCheckIn = false;
        log.info("现在时间为： " + new Date() + " ， 之后不再允许打卡！ ");
        System.out.println("现在时间为： " + new Date() + " ， 之后不再允许打卡！ ");

        // todo 更新打卡投资收益信息
        updateTFund();
        BonusPoolUtil.isOpen = true;
        log.info(" 更新获取资金池信息状态为 isOpen = " + BonusPoolUtil.isOpen);
    }

    @Scheduled(cron = "0 00 05 ? * *")
    // 每天早上8点半以后不允许打卡
    public void canEarlyGetUpCheckIn(){
        CheckInUtil.canCheckIn = true;
        log.info("现在时间为： " + new Date() + " ， 开始允许打卡！ ");
        System.out.println("现在时间为： " + new Date() + " ， 开始允许打卡！ ");
    }

    @Scheduled(cron = "00 00 00 ? * *")
    public void canNotGetBonusPoolInfo(){
        // 此时不能获取资金池信息
        BonusPoolUtil.isOpen = false;
        BonusPoolUtil.setTotalBonus(0.0f);
        BonusPoolUtil.setNumOfPeopleToDevide(0);
        log.info(" 更新获取资金池信息状态为 isOpen = " + BonusPoolUtil.isOpen);
    }


    private void updateTFund(){
        // 获得 成功和失败的id
        Set<Integer> successIds = earlyGetUpService.getSuccessIds();
        Set<Integer> failedIds = earlyGetUpService.getFailedIds();

        // 获得总的资金池
        float totalBonus = (failedIds == null || failedIds.size() == 0) ?
                0 : tFundService.getBonusPoolOfTodayByIds(failedIds);

        BonusPoolUtil.setTotalBonus(totalBonus);
        BonusPoolUtil.setNumOfPeopleToDevide(successIds.size());

        if(BonusPoolUtil.getNumOfPeopleToDevide() > 0) {
            float eachIncome = BonusPoolUtil.getTotalBonus() / BonusPoolUtil.getNumOfPeopleToDevide();

            Set<Integer> totalIds = new HashSet<>();
            totalIds.addAll(successIds);
            totalIds.addAll(failedIds);
            List<Fund> funds = tFundService.getAllEarlyUpCheckInToday(totalIds);

            totalIds.clear();
            for (Fund fund : funds) {
                if (successIds.contains(fund.getUserId())) {
                    // 成功的人，获得今日平均收益
                    fund.setEarlyUpIncome(eachIncome);
                    fund.setIncome(fund.getIncome() + eachIncome);
                    fund.setTotalFund(fund.getTotalFund() + eachIncome);
                } else {
                    // 失败的人, 减去今日投资
                    fund.setEarlyUpIncome(0 - fund.getEarlyUpInvestTod());
                    fund.setIncome(fund.getIncome() - fund.getEarlyUpInvestTod());
                    fund.setTotalFund(fund.getTotalFund() - fund.getEarlyUpInvestTod());
                }
            }

            // 批量更新资产表, 一次更新100条
            int step = 100;
            for (int i = 0; i < funds.size(); i = i + step) {
                int endIndex = i + step < funds.size() ? i + step : funds.size();
                tFundService.updateBatch(funds.subList(i, endIndex));
            }

        }
        log.info(" 资产已经根据早起打卡情况更新完毕~~");
    }

}
