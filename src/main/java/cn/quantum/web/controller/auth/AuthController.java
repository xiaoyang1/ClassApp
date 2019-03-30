package cn.quantum.web.controller.auth;

import cn.quantum.annotation.BusinessLog;
import cn.quantum.constant.CommonConstant;
import cn.quantum.constant.StringConstant;
import cn.quantum.constant.TokenConstant;
import cn.quantum.enums.ResultEnum;
import cn.quantum.exception.AuthorizeException;
import cn.quantum.util.CommonUtil;
import cn.quantum.util.ResultVOUtil;
import cn.quantum.util.TokenManageUtil;
import cn.quantum.web.db.domain.TUser;
import cn.quantum.web.service.TAuthService;
import cn.quantum.web.service.TUserService;
import cn.quantum.web.vo.ResultVO;
import cn.quantum.web.vo.TUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/tuser")
@BusinessLog("用户登录记录")
@Slf4j
public class AuthController {

    @Autowired
    private TAuthService tAuthService;

    @Autowired
    private TUserService tUserService;
    /**
     * 添加用户
     *
     * @return
     */
    @PostMapping(value = "/register")
    //@Valid CategoryForm form,BindingResult bindingResult
    @BusinessLog("注册用户")
    public ResultVO addUser(@RequestBody TUser user){
        if(user == null){
            return ResultVOUtil.error(0, "没有获得所需要的参数");
        }
        if(StringUtils.isEmpty(user.getUsername())){
            return ResultVOUtil.error(0, "用户账号不能为空");
        }
        if(StringUtils.isEmpty(user.getPassword())){
            return ResultVOUtil.error(0, "用户密码不能为空");
        }
        if(StringUtils.isEmpty(user.getEntname())){
            return ResultVOUtil.error(0, "用户昵称不能为空");
        }

        // 验证该账号是否被注册


        // 加密
        user.setPassword(CommonUtil.MD5(user.getPassword()));
        tUserService.addUser(user);
        TUserVO vo = tUserService.findUserVOById(user.getId());
        log.info("新注册用户: " + vo);
        return ResultVOUtil.success(vo);
    }

    /**
     *
     * @param username
     * @param password
     * @param request
     * @param reponse
     * @return
     */
    @BusinessLog("登陆")
    @RequestMapping(value = "/login")
    public ResultVO userlogin(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              HttpServletRequest request, HttpServletResponse reponse){

        return tAuthService.userLogin(username, password, request, reponse);
    }

    @BusinessLog("退出")
    @RequestMapping("/logout")
    public ResultVO userlogout(HttpServletRequest request, HttpServletResponse response) {
        return tAuthService.userLogout(request, response);
    }

    @RequestMapping("/getInfo")
    public ResultVO getUserInfo(HttpServletRequest request){
        String token = request.getHeader(TokenConstant.TOKEN);
        if(StringUtils.isEmpty(token)){
            throw new AuthorizeException(CommonConstant.USER_NOT_LOGIN, ResultEnum.NOT_LOGIN);
        }

        TUserVO userVO = (TUserVO) TokenManageUtil.get(token);

        if(userVO != null){

            System.out.println(userVO);
            log.info(String.format(StringConstant.GET_USER_INFO, userVO.getUsername()));
            return ResultVOUtil.success(userVO);
        }

        throw new AuthorizeException(CommonConstant.USER_NOT_LOGIN, ResultEnum.NOT_LOGIN);
    }

}
