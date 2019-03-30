package cn.quantum.web.db.mapper;

import cn.quantum.web.db.domain.TSport;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface TSportMapper {
    @Select("select userId, ifToday, ifComplete, ifDo from t_sport where userId = #{userId}")
    TSport getByUserId(Integer userId);

    @Update("update t_sport set ifToday = #{ifToday} where userId = #{userId}")
    void updateToday(TSport tSport);

    @Update("update t_sport set ifComplete = #{ifComplete}, ifDo = #{ifDo} where userId = #{userId}")
    void updateCompleteByUserId(TSport tSport);

    @Insert("insert into t_sport(userId, ifToday) values (#{userId}, #{ifToday})")
    void insertToday(TSport tSport);

    @Select("select sum(ifToday) today, sum(ifComplete) complete from t_sport")
    Map<String, Object> getCount();
}
