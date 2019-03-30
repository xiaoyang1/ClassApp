package cn.quantum.web.db.domain;

import lombok.Data;

@Data
public class TSport {
    private int userId;       // 用户id

    private int ifToday;      // 今天是否设置打卡任务

    private int ifComplete;   // 今天任务是否完成

    private int ifDo;         // 是否已经打卡

    public TSport() {
    }

    public TSport(int userId) {
        this.userId = userId;
    }
}
