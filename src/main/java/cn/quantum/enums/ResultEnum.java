package cn.quantum.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    SUCCESS("成功"),

    PARAM_ERROR("参数不正确"),

    LOGIN_FAIL("登录失败，用户名或密码不正确"),

    USER_NOT_FIND("该用户不存在"),

    LOGOUT_SUCCESS("用户退出成功"),

    NOT_LOGIN("用户未登录");

    private String message;

    ResultEnum(String message) {
        this.message = message;
    }
}
