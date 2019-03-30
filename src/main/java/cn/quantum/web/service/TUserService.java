package cn.quantum.web.service;


import cn.quantum.web.db.domain.TUser;
import cn.quantum.web.vo.ResultVO;
import cn.quantum.web.vo.TUserVO;

import javax.servlet.http.HttpServletRequest;

public interface TUserService {

    ResultVO addUser(TUser user);

    TUserVO findUserVOById(Integer id);

    ResultVO updatePW(String oldPassword, String newPassword, HttpServletRequest request);

    boolean updatePassword(Integer id, String pwDb);

    ResultVO modifyAntName(HttpServletRequest request, String entName);

}
