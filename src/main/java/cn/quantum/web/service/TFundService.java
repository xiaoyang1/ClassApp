package cn.quantum.web.service;

import cn.quantum.web.db.domain.Fund;
import cn.quantum.web.vo.ResultVO;

import java.util.Collection;
import java.util.List;

public interface TFundService {
    void createFundForUser(Integer id);

    ResultVO getInvestation(Integer id);

    ResultVO getIncome(Integer id);

    Fund sportIncome(int userId, float income);

    void updateEarlyUpInvestTom(Fund fund);

    float getBonusPoolOfTodayByIds(Collection<Integer> ids);

    List<Fund> getAllEarlyUpCheckInToday(Collection<Integer> ids);

    void updateBatch(List<Fund> funds);
}
