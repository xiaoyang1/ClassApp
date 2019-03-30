package cn.quantum.util;

import cn.quantum.web.db.mapper.EarlyGetUpMapper;
import cn.quantum.web.db.mapper.TFundMapper;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 *  这个是控制奖金池， 每天0点到8点半期间，不公布数据，
 */
@Slf4j
public class BonusPoolUtil {
    private static float totalBonus;          // 瓜分的总金额
    private static int numOfPeopleToDevide;   // 瓜分人的总数
    public static boolean isOpen;

    static {
        // 在加载的时候判断时间，如果在早上0点到八点半，就不允许查看奖金池，
        // 避免工程在这个时间段重启后不能打卡的bug
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        Date now =null;
        Date beginTime = null;
        Date endTime = null;

        try {
            now = df.parse(df.format(new Date()));
            beginTime = df.parse("00:00");
            endTime = df.parse("08:30");

            isOpen = !CommonUtil.belongCalendar(now, beginTime, endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info(new Date() + "  : " + "当前是否可查看总奖金池的状态为： " + isOpen);

        // 如果可查看，那么从数据库获得总奖金池的钱数
        if(isOpen) {
            // todo 从数据库获得总奖金池的钱数
            TFundMapper tFundMapper = (TFundMapper) SpringContextUtil.getBean(TFundMapper.class);
            EarlyGetUpMapper earlyGetUpMapper = (EarlyGetUpMapper) SpringContextUtil.getBean(EarlyGetUpMapper.class);
            numOfPeopleToDevide = earlyGetUpMapper.getSuccessIds().size();

            Set failed = earlyGetUpMapper.getFailedIds();
            int faliedCount = failed.size();
            if(faliedCount > 0) {
                totalBonus = tFundMapper.getBonusPoolOfTodayByIds(failed);
            }else {
                totalBonus = 0;
                log.info("totalBonus = 0, earlyGetUpMapper.getFailedIds() = " + faliedCount);
            }

            log.info("奖金池初始化结束， 瓜分的总金额 totalBonus = " + totalBonus + " , 瓜分人数 numOfPeopleToDevide " + numOfPeopleToDevide);
        }

    }

    public static int getNumOfPeopleToDevide(){
        return numOfPeopleToDevide;
    }

    public static void setNumOfPeopleToDevide(int numOfPeopleToDevide){
        BonusPoolUtil.numOfPeopleToDevide = numOfPeopleToDevide;
    }

    public static float getTotalBonus(){
        return totalBonus;
    }

    public static void setTotalBonus(float totalBonus){
        BonusPoolUtil.totalBonus = totalBonus;
    }
}
