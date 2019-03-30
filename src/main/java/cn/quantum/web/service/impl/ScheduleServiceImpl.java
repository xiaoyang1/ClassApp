package cn.quantum.web.service.impl;

import cn.quantum.util.ResultVOUtil;
import cn.quantum.web.db.dao.ScheduleDao;
import cn.quantum.web.db.domain.Schedule;
import cn.quantum.web.service.ScheduleService;
import cn.quantum.web.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ScheduleServiceImpl implements ScheduleService{

    @Autowired
    private ScheduleDao scheduleDao;

    @Override
    public Schedule getScheduleById(int userId) {
        return scheduleDao.getScheduleById(userId);
    }

    @Override
    public ResultVO updateSchedule(int userId, String classSchedule) {
        Schedule schedule = getScheduleById(userId);
        if(schedule == null){
            schedule = new Schedule(userId, classSchedule);
            scheduleDao.insertSchedule(schedule);
        } else {
            schedule.setSchedule(classSchedule);
            scheduleDao.updateSchedule(schedule);
        }
        log.info(schedule.getUserId() + "更新课程表为：" + schedule.getSchedule());
        return ResultVOUtil.success();
    }

    @Override
    public void deleteById(int userId) {
        scheduleDao.deleteById(userId);
    }

    @Override
    public void insertSchedule(Schedule schedule) {
        scheduleDao.insertSchedule(schedule);
        log.info(schedule.getUserId() + "插入一张课程表：" + schedule.getSchedule());
    }
}
