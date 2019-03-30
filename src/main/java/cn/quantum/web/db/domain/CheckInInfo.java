package cn.quantum.web.db.domain;

import lombok.Data;

/**
 * 该类是描述用户打卡状态的表映射
 */

@Data
public class CheckInInfo {

    private int userId;

    private int countEarlyGetUp;

    private int successEarlyGetUp;

    private int countSport;

    private int successSport;

    public CheckInInfo() {
    }

    public CheckInInfo(int userId) {
        this.userId = userId;
    }
}
