package cn.quantum.aspect;

import cn.quantum.constant.CommonConstant;
import cn.quantum.constant.TokenConstant;
import cn.quantum.enums.ResultEnum;
import cn.quantum.exception.AuthorizeException;
import cn.quantum.util.TokenManageUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Aspect
@Component
@Slf4j
public class AuthorizeAspect {


    /**
     * 全站未登录拦截
     */
    @Pointcut("execution(public * cn.quantum.web.controller..*(..)) && " +
            "!execution(public * cn.quantum.web.controller.auth..*.*(..)) && " +
            "!execution(public * cn.quantum.web.controller..*.getNewest(..))")
    public void verify() {
    }

    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();

        String token = request.getHeader(TokenConstant.TOKEN);

        if (StringUtils.isEmpty(token) || !TokenManageUtil.exists(token)){
            log.error("【登录校验】Cookie和header中查不到用户登录的token");
            throw new AuthorizeException(CommonConstant.USER_NOT_LOGIN, ResultEnum.NOT_LOGIN);
        } else {
            request.setAttribute(TokenConstant.TOKEN, token);
            response.setHeader(TokenConstant.TOKEN, token);
        }
    }
}
