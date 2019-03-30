package cn.quantum.web.db.dao;

import cn.quantum.web.db.domain.Schedule;
import cn.quantum.web.db.mapper.TScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduleDao {

    @Autowired
    private TScheduleMapper tScheduleMapper;

    public Schedule getScheduleById(int userId){
        return tScheduleMapper.getScheduleById(userId);
    }

    public void updateSchedule(Schedule schedule){
        tScheduleMapper.updateSchedule(schedule);
    }

    public void deleteById(int userId){
        tScheduleMapper.deleteById(userId);
    }

    public void insertSchedule(Schedule schedule){
        tScheduleMapper.insertSchedule(schedule);
    }
}
