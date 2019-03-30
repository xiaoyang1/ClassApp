package cn.quantum.web.service;

import cn.quantum.web.db.domain.Schedule;
import cn.quantum.web.vo.ResultVO;

public interface ScheduleService {

    Schedule getScheduleById(int userId);

    ResultVO updateSchedule(int userId, String classSchedule);

    void deleteById(int userId);

    void insertSchedule(Schedule schedule);
}
