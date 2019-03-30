package cn.quantum.web.db.mapper;

import cn.quantum.web.db.domain.TUser;
import cn.quantum.web.vo.TUserVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface TUserMapper {

    @Select("select id, username, password, entname, registerTime, email, isdeleted from t_user where id = #{id} and isdeleted = 0 limit 1")
    TUser findById(Integer id);

    @Select("select id, username, password, entname, registerTime from t_user where id = #{id} and isdeleted = 0 limit 1")
    TUserVO findUserVOById(Integer id);

    @Select("select id, username, password, entname, registerTime from t_user where username = #{username} and isdeleted = 0")
    TUserVO findByUsername(String userName);

    @Insert("insert into t_user(username, password, entname) values(#{username}, #{password}, #{entname})")
    @Options(useGeneratedKeys = true)
    boolean addUser(TUser user);

    @Update("update t_user set password = #{password} where id = #{id}")
    boolean updatePassword(@Param("id") Integer id, @Param("password") String password);

    @Update("update t_user set entname = #{entname} where id = #{id}")
    boolean modifyAntName(@Param("id") Integer id, @Param("entname") String entName);

    @Select("select id, email from t_user where id = #{id} and isdeleted = 0 limit 1")
    TUser getEmailById(Integer id);

    @Update("update t_user set email = #{email} where id = #{id} and isdeleted = 0 limit 1")
    void updateEmail(@Param("id") Integer id, @Param("email") String email);

    @Update("update t_user set email = null where id = #{id}")
    void deleteEmailById(Integer id);

    @Select("select id, username, password, email from t_user where username = #{username} and isdeleted = 0 limit 1")
    TUser getEmailByUsername(String username);
}
