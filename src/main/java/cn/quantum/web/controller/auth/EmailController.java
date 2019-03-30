package cn.quantum.web.controller.auth;

import cn.quantum.annotation.BusinessLog;
import cn.quantum.constant.CommonConstant;
import cn.quantum.constant.StringConstant;
import cn.quantum.constant.TokenConstant;
import cn.quantum.enums.ResultEnum;
import cn.quantum.exception.AuthorizeException;
import cn.quantum.util.*;
import cn.quantum.web.db.dao.TUserDao;
import cn.quantum.web.db.domain.TUser;
import cn.quantum.web.service.EmailService;
import cn.quantum.web.service.TUserService;
import cn.quantum.web.vo.ResultVO;
import cn.quantum.web.vo.TUserVO;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 *  这个主要是用来邮箱管理的，主要是注册的时候邮箱验证，找回密码
 */

@RestController
@RequestMapping("/temail")
@BusinessLog("邮箱管理")
@Slf4j
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private TUserDao tUserDao;

    /**
     *  获得验证码
     */
    @RequestMapping("/getValidCode")
    public ResultVO getValidCode(@NonNull String emailAddress){
        if(!CommonUtil.checkEmailAddress(emailAddress)){
            return ResultVOUtil.error(0, "邮箱地址格式不对！");
        }

        // todo 生成验证码，6位
        String validCode = generateCode(6, false);
        // TODO 加入邮件发送任务，并将验证码加入缓存中，90s 消失
        emailService.sendSimpleMail(emailAddress, "验证码", String.format(StringConstant.EMAIL_VALIDATE, validCode));
        ValidCodeUtil.put(emailAddress, validCode);
        // todo  返回前端验证码
        return ResultVOUtil.success(validCode);
    }

    @RequestMapping("/bindEmail")
    public ResultVO bindEmail(@NonNull String emailAddress, @NonNull String validCode, HttpServletRequest request){
        String token = request.getHeader(TokenConstant.TOKEN);
        if(StringUtils.isEmpty(token)){
            throw new AuthorizeException(CommonConstant.USER_NOT_LOGIN, ResultEnum.NOT_LOGIN);
        }

        // todo  从缓存中校验邮箱和校验码

        if(!validate(emailAddress, validCode)){
            return ResultVOUtil.error(0, "验证码失效~");
        }

        // 获得是否存在绑定邮箱
        TUserVO userVO = (TUserVO) TokenManageUtil.get(token);
        String validatedEmail = emailService.getEmailById(userVO.getId()).getEmail();
        if(!StringUtils.isEmpty(validatedEmail)){
            return ResultVOUtil.error(0, "该用户已经绑定了一个邮箱，在未解绑之前请勿重复绑定!");
        }

        // todo 校验邮箱格式是否正确,写入数据库，
        if(!CommonUtil.checkEmailAddress(emailAddress)){
            return ResultVOUtil.error(0, "请输入正确的邮箱格式！");
        }
        try {
            emailService.updateEmail(userVO.getId(), emailAddress);
            // todo 发送邮件，通知邮箱绑定成功
            emailService.sendSimpleMail(emailAddress, "邮箱绑定", String.format(StringConstant.EMAIL_BIND, emailAddress));
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }
        log.info("用户已经绑定邮箱为： " + emailAddress + "， 已发送邮件通知~");
        return ResultVOUtil.success();
    }

    /**
     *  解除绑定邮箱之前先调用获取绑定邮箱操作，
     * @param emailAddress
     * @param validCode
     * @param request
     * @return
     */
    @RequestMapping("/unbind")
    public ResultVO unbindEmail(@NonNull String emailAddress, @NonNull String validCode, HttpServletRequest request){
        // todo 验证是否登录， 验证该邮箱是否被绑定
        String token = request.getHeader(TokenConstant.TOKEN);
        if(StringUtils.isEmpty(token)){
            throw new AuthorizeException(CommonConstant.USER_NOT_LOGIN, ResultEnum.NOT_LOGIN);
        }
        // 获得是否存在绑定邮箱
        TUserVO userVO = (TUserVO) TokenManageUtil.get(token);
        String validatedEmail = emailService.getEmailById(userVO.getId()).getEmail();
        if(!emailAddress.equals(validatedEmail)){
            return ResultVOUtil.error(0, "指定的邮箱并没有绑定~");
        }
        // todo 从缓存中校验邮箱和校验码
        if(!validate(emailAddress, validCode)){
            return ResultVOUtil.error(0, "验证码失效~~请重新获取并上传");
        }

        // todo 删除 数据库信息， 发送邮件通知，
        emailService.deleteEmailById(userVO.getId());
        emailService.sendSimpleMail(emailAddress, "邮箱解除绑定", String.format(StringConstant.EMAIL_UNBIND, emailAddress));

        log.info("用户已经解除绑定邮箱： " + emailAddress + "， 已发送邮件通知~");
        return ResultVOUtil.success();
    }

    @RequestMapping("/getBindEmail")
    public ResultVO getBindEmail(HttpServletRequest request){
        // 获取token，验证是否登录，
        String token = request.getHeader(TokenConstant.TOKEN);
        if(StringUtils.isEmpty(token)){
            throw new AuthorizeException(CommonConstant.USER_NOT_LOGIN, ResultEnum.NOT_LOGIN);
        }
        // 获得是否存在绑定邮箱
        TUserVO userVO = (TUserVO) TokenManageUtil.get(token);
        log.info("用户 " + userVO.getUsername() + " 获取了绑定邮箱的信息~");
        if(userVO != null){
            TUser user = emailService.getEmailById(userVO.getId());
            if(user != null && !StringUtils.isEmpty(user.getEmail())){
                return ResultVOUtil.success(user.getEmail());
            }
            return ResultVOUtil.error(0, "不存在相关的邮箱绑定信息~");
        }

        throw new AuthorizeException(CommonConstant.USER_NOT_LOGIN, ResultEnum.NOT_LOGIN);
    }

    @RequestMapping("/getPasswd")
    public ResultVO getpasswd(@NonNull String emailAddress, @NonNull String validCode, @NonNull String username){
        // 从缓存中校验 校验码和邮箱，
        if(!validate(emailAddress, validCode)){
            return ResultVOUtil.error(0, "验证码失效~~请重新获取并上传");
        }
        // 校验邮箱和对应账号的绑定，
        TUser user = emailService.getEmailByUsername(username);
        if(user == null){
            return ResultVOUtil.error(0, "不存在该用户信息~");
        }
        if(!emailAddress.equals(user.getEmail())){
            return ResultVOUtil.error(0, "该用户未绑定该邮箱~");
        }


        String tempPasswd = generateCode(8, true);
        try {
            // 暂时更新数据库密码
            tUserDao.updatePassword(user.getId(), CommonUtil.MD5(tempPasswd));
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            return ResultVOUtil.error(0, "内部错误~~");
        }
        // 获得密码， 通过邮件返回密码
        emailService.sendSimpleMail(emailAddress, "找回密码", String.format(StringConstant.EMAIL_PW, tempPasswd));
        log.info("用户 " + username + " 通过绑定邮箱获取了密码~");
        return ResultVOUtil.success();
    }

    private boolean validate(String emailAddress, String validCode){
        if (ValidCodeUtil.isExist(emailAddress) && validCode.equals(ValidCodeUtil.get(emailAddress))){
            return true;
        }
        return false;
    }

    private String generateCode(int length, boolean isPasswd){

//        String str="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String str="0123456789";
        if(isPasswd){
            str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        }
        StringBuilder sb = new StringBuilder(length);
        for(int i = 0;i < length;i++)
        {
            char ch = str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }

        return sb.toString();
    }
}
