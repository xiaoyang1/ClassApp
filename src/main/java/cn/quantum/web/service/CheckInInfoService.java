package cn.quantum.web.service;

import cn.quantum.web.db.domain.CheckInInfo;

public interface CheckInInfoService {

    CheckInInfo findInfoById(int userId);

    void updateEarlyUpInfo(CheckInInfo selfInfo);

    void insertEarlyUpOne(CheckInInfo selfInfo);

    void updateSportInfo(CheckInInfo selfInfo);

    void insertSportOne(CheckInInfo selfInfo);
}
