package cn.quantum.util;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

/**
 * 一些常用的工具方法
 */
public class CommonUtil {

    /**
     * @return
     * @Title: createGuid
     * @Description: 生成唯一的32位的ID
     * @return:String
     * @version: v1.0.0
     */


    public static String createGuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

    /**
     * 检验字符串
     */
    public static boolean isValid(Object str) {
        if (str == null || "".equals(str.toString()) || "null".equalsIgnoreCase(str.toString().trim())) {
            return false;
        }
        return true;
    }

    /**
     * 检验字符串
     */
    public static boolean isValid(String str) {
        if (str == null || "".equals(str.trim()) || "null".equalsIgnoreCase(str.trim())) {
            return false;
        }
        return true;
    }
    // 获取当前时间字符串
    public static String getNowDateStr() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.DATE);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);

        return "" + year + month + day + hour + minute;
    }

    // 生成唯一的文件名
    public static String getUniqueFileName() {
        String str = UUID.randomUUID().toString();
        return str.replace("-", "").substring(20);
    }


    // 密码加密
    public static String MD5(String src) {
        if (StringUtils.isEmpty(src))
            return "";
        return DigestUtils.md5Hex(src).toUpperCase();
    }

    public static String MD5Decode(String md5Str){
        return null;
    }

    /**
     * 判断时间是否在时间段内
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkEmailAddress(String emailAddress){
        String regex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(emailAddress);
        return matcher.matches();
    }
}
