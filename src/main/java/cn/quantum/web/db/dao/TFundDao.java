package cn.quantum.web.db.dao;

import cn.quantum.web.db.domain.Fund;
import cn.quantum.web.db.mapper.TFundMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class TFundDao {

    @Autowired
    private TFundMapper tFundMapper;

    public void createFundForUser(Integer userId) {
        tFundMapper.insertOne(userId);
    }

    public Fund getInvestation(Integer userId) {
        return tFundMapper.getInvestById(userId);
    }

    public Fund getIncome(Integer userId) {
        return tFundMapper.getIncomeById(userId);
    }

    public void updateSportIncome(Fund fund) {
        tFundMapper.updateSportIncome(fund);
    }

    public void updateEarlyUpInvestTom(Fund fund){
        // 更新第二天预约打卡投资
        tFundMapper.updateEarlyUpInvestTom(fund);
    }

    public float getBonusPoolOfTodayByIds(Collection<Integer> ids){
        return tFundMapper.getBonusPoolOfTodayByIds(ids);
    }

    public List<Fund> getAllEarlyUpCheckInToday(Collection<Integer> ids){
        return tFundMapper.getAllEarlyUpCheckInToday(ids);
    }

    public void  updateBatch(List<Fund> funds){
        tFundMapper.updateBatch(funds);
    }
}
