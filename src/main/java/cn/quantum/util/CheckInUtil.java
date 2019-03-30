package cn.quantum.util;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *  这个类用来设置定时任务状态，比如，在早上5点到8点可以打卡，
 */
@Slf4j
public class CheckInUtil {
    public static boolean canCheckIn = false;

    static {
        // 在加载的时候判断时间，如果在早上5点八点半，就允许打卡，
        // 避免工程在这个时间段重启后不能打卡的bug
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        Date now =null;
        Date beginTime = null;
        Date endTime = null;

        try {
            now = df.parse(df.format(new Date()));
            beginTime = df.parse("05:00");
            endTime = df.parse("08:30");

            canCheckIn = CommonUtil.belongCalendar(now, beginTime, endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("当前打卡状态为： " + canCheckIn);
        log.info(new Date() + "  : " + "当前打卡状态为： " + canCheckIn);
    }

}
