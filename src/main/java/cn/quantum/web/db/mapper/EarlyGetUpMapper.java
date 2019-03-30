package cn.quantum.web.db.mapper;

import cn.quantum.web.db.domain.EarlyGetUp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;

@Repository
public interface EarlyGetUpMapper {

    @Select("select userId, ifToday, ifComplete, ifTomorrow, ifDo from t_early_get_up where userId = #{userId}")
    EarlyGetUp getByUserId(Integer userId);

    @Update("update t_early_get_up set ifTomorrow = #{ifTomorrow} where userId = #{userId}")
    void updateTomorrowByUserId(EarlyGetUp earlyGetUp);

    @Update("update t_early_get_up set ifComplete = #{ifComplete}, ifDo = #{ifDo} where userId = #{userId}")
    void updateCompleteByUserId(EarlyGetUp earlyGetUp);

    @Insert("insert into t_early_get_up(userId, ifTomorrow) values (#{userId}, #{ifTomorrow})")
    void insertTomorrow(EarlyGetUp earlyGetUp);

    @Select("select sum(ifToday) today, sum(ifComplete) complete, sum(ifTomorrow) tomorrow from t_early_get_up")
    Map<String, Object> getCount();

    @Select("select userId from t_early_get_up where ifComplete = 1 and ifToday = 1")
    Set<Integer> getSuccessIds();

    @Select("select userId from t_early_get_up where ifComplete = 0 and ifToday = 1")
    Set<Integer> getFailedIds();

}
