package cn.quantum.web.db.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class TUser {
    @Id
    private Integer id;         // 唯一主键

    private String username;    // 用户名称

    private String password;    // 用户密码

    private Date registerTime;     // 注册时间

    private String entname;     // 用户昵称

    private Integer isdeleted;    // 是否被删除标记

    private String email;
}
