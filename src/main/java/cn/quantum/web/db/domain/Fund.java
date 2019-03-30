package cn.quantum.web.db.domain;

import lombok.Data;

@Data
public class Fund {

    private Integer userId;
    private Float totalFund;       // 个人总资产
    private Float earlyUpInvestTod; // 今日早起投资
    private Float earlyUpInvestTom; // 明日早起投资
    private Float earlyUpIncome;   // 今日早起收入
    private Float sportIncome;   // 运动收益
    private Float income;

    public Fund() {
    }

    public Fund(int userId) {
        this.userId = userId;
    }
}
