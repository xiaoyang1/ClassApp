package cn.quantum.web.db.mapper;

import cn.quantum.web.db.domain.Schedule;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface TScheduleMapper {
    @Select("select userId, schedule, lastModTime from t_schedule where userId = #{userId} and isdeleted = 0")
    Schedule getScheduleById(int userId);

    @Update("update t_schedule set schedule = #{schedule} where userId = #{userId} and isdeleted = 0 ")
    void updateSchedule(Schedule schedule);

    @Delete("update t_schedule set isdeleted = 1 where userId = #{userId} and isdeleted = 0")
    void deleteById(int userId);

    @Insert("insert into t_schedule(userId, schedule) values(#{userId}, #{schedule})")
    void insertSchedule(Schedule schedule);
}
