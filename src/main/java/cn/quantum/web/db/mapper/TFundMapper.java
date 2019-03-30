package cn.quantum.web.db.mapper;

import cn.quantum.web.db.domain.Fund;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TFundMapper {
    @Insert("insert into t_fund(userId) values (#{userId})")
    void insertOne(int userId);

    @Select("select userId, totalFund, earlyUpInvestTod, earlyUpInvestTom from t_fund where userId = #{userId}")
    Fund getInvestById(int userId);

    @Update("update t_fund set sportIncome = #{sportIncome}, income = #{income} where userId = #{userId}")
    void updateSportIncome(Fund fund);

    @Select("select userId, totalFund, earlyUpIncome, sportIncome, income from t_fund where userId = #{userId}")
    Fund getIncomeById(int userId);

    @Update("update t_fund set earlyUpInvestTom = #{earlyUpInvestTom} where userId = #{userId}")
    void updateEarlyUpInvestTom(Fund fund);

    // count 是计数，，sum才是求和
    @Select("<script> select sum(earlyUpInvestTod) from t_fund where userId IN "
            + "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach>"
            + "</script>")
    float getBonusPoolOfTodayByIds(@Param("ids") Collection<Integer> ids);


    @Select("<script> select userId, earlyUpInvestTod, earlyUpIncome, income, totalFund from t_fund where userId IN "
            + "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>"
            + "${item}"
            + "</foreach>"
            + "</script>")
    List<Fund> getAllEarlyUpCheckInToday(@Param("ids") Collection<Integer> ids);


    @Update("<script>"
            + "<foreach item='item' index='index' collection='funds' open='' separator=';' close=''>"
            + "update t_fund "
            + "<set> earlyUpIncome = #{item.earlyUpIncome}, income = #{item.income}, totalFund = #{item.totalFund} </set>"
            + "where userId = #{item.userId}"
            + "</foreach>"
            + "</script>")
    void updateBatch(@Param("funds") List<Fund> funds);
}
