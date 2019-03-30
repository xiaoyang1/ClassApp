package cn.quantum.web.db.mapper;

import cn.quantum.web.db.domain.Wisdom;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface WisdomMapper {
    // 多个参数要么用对象，要么用map， 要么用@Param 注解
    @Options(useGeneratedKeys = true)
    @Insert("insert into t_wisdom(content, url) values(#{content}, #{url})")
    public void insertOne(@Param("content") String content, @Param("url")String url);

    @Select("select id, content, url from t_wisdom order by id desc limit 1")
    public Wisdom findLast();
}
