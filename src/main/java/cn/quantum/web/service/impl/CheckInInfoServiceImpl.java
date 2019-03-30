package cn.quantum.web.service.impl;

import cn.quantum.web.db.dao.CheckInInfoDao;
import cn.quantum.web.db.domain.CheckInInfo;
import cn.quantum.web.service.CheckInInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CheckInInfoServiceImpl implements CheckInInfoService{
    @Autowired
    private CheckInInfoDao checkInInfoDao;

    @Override
    public CheckInInfo findInfoById(int userId) {
        return checkInInfoDao.findInfoById(userId);
    }

    @Override
    public void updateEarlyUpInfo(CheckInInfo selfInfo) {
        checkInInfoDao.updateEarlyUpInfo(selfInfo);
    }

    @Override
    public void insertEarlyUpOne(CheckInInfo selfInfo) {
        checkInInfoDao.insertEarlyUpOne(selfInfo);
    }

    @Override
    public void updateSportInfo(CheckInInfo selfInfo) {
        checkInInfoDao.updateSportInfo(selfInfo);
    }

    @Override
    public void insertSportOne(CheckInInfo selfInfo) {
        checkInInfoDao.insertSportOne(selfInfo);
    }
}
