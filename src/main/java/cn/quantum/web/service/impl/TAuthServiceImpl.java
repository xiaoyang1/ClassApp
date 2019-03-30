package cn.quantum.web.service.impl;

import cn.quantum.constant.CommonConstant;
import cn.quantum.constant.StringConstant;
import cn.quantum.constant.TokenConstant;
import cn.quantum.enums.ResultEnum;
import cn.quantum.exception.AuthorizeException;
import cn.quantum.util.CommonUtil;
import cn.quantum.util.ResultVOUtil;
import cn.quantum.util.TokenManageUtil;
import cn.quantum.web.db.dao.TUserDao;
import cn.quantum.web.db.domain.TUser;
import cn.quantum.web.service.TAuthService;
import cn.quantum.web.vo.ResultVO;
import cn.quantum.web.vo.TUserVO;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
public class TAuthServiceImpl implements TAuthService{
    @Autowired
    private TUserDao tUserDao;


    @Override
    public TUser findById(Integer id) {
        return tUserDao.findById(id);
    }

    @Override
    public ResultVO userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            throw new AuthorizeException(CommonConstant.FAILED, ResultEnum.LOGIN_FAIL.getMessage());
        }

        TUserVO userVO = tUserDao.findByUsername(username);

        if(userVO != null){
            if (!userVO.getUsername().equals(username))
                throw new AuthorizeException(CommonConstant.FAILED, ResultEnum.LOGIN_FAIL.getMessage());
            // 验证密码
            String realPass = userVO.getPassword();
            String md5Pass = CommonUtil.MD5(password);

            if(realPass.equals(md5Pass)){
                String token = request.getHeader(TokenConstant.TOKEN);
                if(StringUtils.isEmpty(token)){
                    token = TokenConstant.TOKEN_KEY + CommonUtil.createGuid();
                } else {
                    TokenManageUtil.remove(token);
                    token = TokenConstant.TOKEN_KEY + CommonUtil.createGuid();
                }

                response.setHeader(TokenConstant.TOKEN, token);
                response.setHeader(TokenConstant.USERNAME, userVO.getUsername());

                // 加入token缓存
                TokenManageUtil.put(token, userVO);

                log.info(String.format(StringConstant.LOGIN, userVO.getUsername()));
                return ResultVOUtil.success(userVO);
            }
        }

        throw new AuthorizeException(CommonConstant.FAILED, ResultEnum.USER_NOT_FIND.getMessage());
    }

    @Override
    public ResultVO userLogout(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(TokenConstant.TOKEN);
        TUserVO userVO = TokenManageUtil.remove(token);
        if(userVO == null)
            return ResultVOUtil.error(0, "用户下线异常，可能没登录？");
        log.info(String.format(StringConstant.LOGINOUT, userVO.getUsername()));
        return ResultVOUtil.success();
    }

    /**
     * 密码校验
     *
     * @param password
     * @param request
     */
    @Override
    public void validatePassword(String password, HttpServletRequest request) {
        if (StringUtils.isEmpty(password)) {
            throw new AuthorizeException(CommonConstant.FAILED, "密码不能为空");
        }

        TUserVO userInfo = getUserInfo(request);
        TUser tUser = findById(userInfo.getId());
        if(!tUser.getPassword().equalsIgnoreCase(CommonUtil.MD5(password))){
            throw new AuthorizeException(CommonConstant.FAILED, "密码错误");
        }
    }

    @Override
    public TUserVO getUserInfo(HttpServletRequest request) {
        String token = request.getHeader(TokenConstant.TOKEN);
        TUserVO userVO = getUserInfo(token);
        return userVO;
    }

    @Override
    public TUserVO getUserInfo(String token) {
        TUserVO userVO = getUserInfoFromCache(token);
        return userVO;
    }


    /**
     * 从缓存中拉取用户信息
     *
     * @param token
     * @return
     */
    private TUserVO getUserInfoFromCache(String token){
        if(StringUtils.isEmpty(token)){
            throw new AuthorizeException(CommonConstant.USER_NOT_LOGIN, ResultEnum.NOT_LOGIN);
        }

        try{
            if(TokenManageUtil.exists(token)){
                TUserVO userVO = (TUserVO) TokenManageUtil.get(token);
                return userVO;
            } else{
                throw new AuthorizeException(CommonConstant.USER_NOT_LOGIN, ResultEnum.NOT_LOGIN);
            }
        } catch (Exception e){
            throw new AuthorizeException(CommonConstant.USER_NOT_LOGIN, ResultEnum.NOT_LOGIN);
        }
    }
}
