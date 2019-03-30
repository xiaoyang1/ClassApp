package cn.quantum.web.service.impl;

import cn.quantum.util.ResultVOUtil;
import cn.quantum.web.db.dao.TFundDao;
import cn.quantum.web.db.domain.Fund;
import cn.quantum.web.service.TFundService;
import cn.quantum.web.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class TFundServiceImpl implements TFundService{

    @Autowired
    private TFundDao tFundDao;

    @Override
    public void createFundForUser(Integer userId) {
        tFundDao.createFundForUser(userId);
    }

    @Override
    public ResultVO getInvestation(Integer userId) {
        Fund fund = tFundDao.getInvestation(userId);

        if(fund != null){
            return ResultVOUtil.success(fund);
        } else {
            return ResultVOUtil.error(0, " 不存在个人资产信息~");
        }
    }

    @Override
    public ResultVO getIncome(Integer userId) {
        Fund fund = tFundDao.getIncome(userId);

        if(fund != null){
            return ResultVOUtil.success(fund);
        } else {
            return ResultVOUtil.error(0, " 不存在个人资产信息~");
        }
    }

    /**
     *  运动打卡收入
     * @param userId
     * @param income
     * @return
     */
    @Override
    @Transactional
    public Fund sportIncome(int userId, float income) {
        Fund fund = tFundDao.getIncome(userId);

        if(fund == null){
            fund = new Fund(userId);
            tFundDao.createFundForUser(userId);
        }

        // 更新运动收益， 当天收入， 总资产
        fund.setSportIncome(fund.getSportIncome() + income);
        fund.setIncome(fund.getIncome() + income);
        fund.setTotalFund(fund.getTotalFund() + income);
        tFundDao.updateSportIncome(fund);

        return fund;
    }

    @Override
    public void updateEarlyUpInvestTom(Fund fund) {
        tFundDao.updateEarlyUpInvestTom(fund);
    }

    @Override
    public float getBonusPoolOfTodayByIds(Collection<Integer> ids) {
        return tFundDao.getBonusPoolOfTodayByIds(ids);
    }

    @Override
    public List<Fund> getAllEarlyUpCheckInToday(Collection<Integer> ids) {
        return tFundDao.getAllEarlyUpCheckInToday(ids);
    }

    @Override
    public void updateBatch(List<Fund> funds) {
        tFundDao.updateBatch(funds);
    }

}
