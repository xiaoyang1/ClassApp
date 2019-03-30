package cn.quantum.web.service;

import cn.quantum.web.db.domain.TUser;
import cn.quantum.web.vo.ResultVO;
import cn.quantum.web.vo.TUserVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface TAuthService {

    TUser findById(Integer id);

    ResultVO userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response);

    ResultVO userLogout(HttpServletRequest request, HttpServletResponse response);

    void validatePassword(String password, HttpServletRequest request);

    TUserVO getUserInfo(HttpServletRequest request);

    TUserVO getUserInfo(String token);
}
