package cn.quantum.constant;

public interface StringConstant {
    String LOGIN = "用户 ：%s 登陆成功了.";  // {}内是username

    String LOGINOUT = "用户 ： %s 已经下线了";

    String GET_USER_INFO = "%s 正在获取用户信息! ";

    String EMAIL_VALIDATE = "您所获取的验证码为： %s , 请您在5分钟内完成邮箱绑定操作，5分钟后该验证码会自动失效，本邮件为系统 自动发出，请勿回复";

    String EMAIL_UNBIND = "你已经成功解除对该邮箱： %s 的绑定~";

    String EMAIL_BIND = "恭喜你成功绑定了邮箱 %s";

    String EMAIL_PW = "恭喜你成功进行邮箱验证， 你获取的临时密码为： %s ，为安全起见，请及时修改密码~ ";
}
