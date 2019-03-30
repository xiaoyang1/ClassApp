package cn.quantum.web.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class TUserVO {

    @JsonProperty("userId")
    private Integer id;

    @JsonProperty("userName")
    private String username;

    private String password;

    @JsonProperty("entName")
    private String entname;

    private Date registerTime;
}
