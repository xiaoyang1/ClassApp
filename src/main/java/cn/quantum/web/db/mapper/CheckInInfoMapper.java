package cn.quantum.web.db.mapper;

import cn.quantum.web.db.domain.CheckInInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckInInfoMapper {

    @Select("select userId, countEarlyGetUp, successEarlyGetUp, countSport, successSport from t_check_in_info where userId = #{userId}")
    public CheckInInfo findInfoById(int userId);

    @Update("update t_check_in_info set countEarlyGetUp = #{countEarlyGetUp}, successEarlyGetUp = #{successEarlyGetUp} where userId = #{userId}")
    void updateEarlyUpInfo(CheckInInfo selfInfo);

    @Insert("insert into t_check_in_info(userId, countEarlyGetUp, successEarlyGetUp) values(#{userId}, #{countEarlyGetUp}, #{successEarlyGetUp} )")
    void insertEarlyUpOne(CheckInInfo selfInfo);

    @Update("update t_check_in_info set countSport = #{countSport}, successSport = #{successSport} where userId = #{userId}")
    void updateSportInfo(CheckInInfo selfInfo);

    @Insert("insert into t_check_in_info(userId, countSport, successSport) values(#{userId}, #{countSport}, #{successSport})")
    void insertSportOne(CheckInInfo selfInfo);
}
