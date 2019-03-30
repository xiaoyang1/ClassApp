package cn.quantum.web.db.dao;

import cn.quantum.web.db.domain.CheckInInfo;
import cn.quantum.web.db.mapper.CheckInInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckInInfoDao {
    @Autowired
    private CheckInInfoMapper checkInInfoMapper;

    public CheckInInfo findInfoById(int userId) {
        return checkInInfoMapper.findInfoById(userId);
    }

    public void updateEarlyUpInfo(CheckInInfo selfInfo) {
        checkInInfoMapper.updateEarlyUpInfo(selfInfo);
    }

    public void insertEarlyUpOne(CheckInInfo selfInfo) {
        checkInInfoMapper.insertEarlyUpOne(selfInfo);
    }

    public void updateSportInfo(CheckInInfo selfInfo){
        checkInInfoMapper.updateSportInfo(selfInfo);
    }

    public void insertSportOne(CheckInInfo selfInfo){
        checkInInfoMapper.insertSportOne(selfInfo);
    }
}
