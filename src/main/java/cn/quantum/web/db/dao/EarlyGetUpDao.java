package cn.quantum.web.db.dao;

import cn.quantum.web.db.domain.EarlyGetUp;
import cn.quantum.web.db.mapper.EarlyGetUpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

@Component
public class EarlyGetUpDao {
    @Autowired
    private EarlyGetUpMapper earlyGetUpMapper;

    public EarlyGetUp getEarlyInfoByUserId(Integer userId) {
        return earlyGetUpMapper.getByUserId(userId);
    }

    public void updateTomorrowByUserId(EarlyGetUp earlyGetUp) {
        earlyGetUpMapper.updateTomorrowByUserId(earlyGetUp);
    }

    public void updateCompleteByUserId(EarlyGetUp earlyGetUp) {
        earlyGetUpMapper.updateCompleteByUserId(earlyGetUp);
    }

    public void insertTomorrow(EarlyGetUp earlyGetUp) {
        earlyGetUpMapper.insertTomorrow(earlyGetUp);
    }

    public Map<String, Object> getCount() {
        return earlyGetUpMapper.getCount();
    }

    public Set<Integer> getSuccessIds(){
        return earlyGetUpMapper.getSuccessIds();
    }

    public Set<Integer> getFailedIds(){
        return earlyGetUpMapper.getFailedIds();
    }

}
