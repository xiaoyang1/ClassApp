package cn.quantum.web.db.domain;

import lombok.Data;

/**
 * 该类是描述用户预约了早起打卡任务的用户信息表映射
 */
@Data
public class EarlyGetUp {

    private int userId;       // 用户id

    private int ifToday;      // 今天是否设置打卡任务

    private int ifComplete;   // 今天任务是否完成

    private int ifTomorrow;   // 明天是否设置打卡任务

    private int ifDo;         // 是否已经打卡

    public EarlyGetUp() {
    }

    public EarlyGetUp(int userId) {
        this.userId = userId;
    }
}
