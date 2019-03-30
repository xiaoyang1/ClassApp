package cn.quantum.web.db.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Schedule {
    private Integer userId;
    private String schedule;
    private Date lastModTime;
    private Integer isdeleted;

    public Schedule(Integer userId, String schedule) {
        this.userId = userId;
        this.schedule = schedule;
    }

    public Schedule() {

    }


}
