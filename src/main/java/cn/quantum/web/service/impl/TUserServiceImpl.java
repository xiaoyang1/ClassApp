package cn.quantum.web.service.impl;

import cn.quantum.constant.CommonConstant;
import cn.quantum.constant.TokenConstant;
import cn.quantum.exception.AuthorizeException;
import cn.quantum.util.CommonUtil;
import cn.quantum.util.ResultVOUtil;
import cn.quantum.util.TokenManageUtil;
import cn.quantum.web.db.dao.TUserDao;
import cn.quantum.web.db.domain.TUser;
import cn.quantum.web.service.TAuthService;
import cn.quantum.web.service.TFundService;
import cn.quantum.web.service.TUserService;
import cn.quantum.web.vo.ResultVO;
import cn.quantum.web.vo.TUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class TUserServiceImpl implements TUserService{
    @Autowired
    private TUserDao tUserDao;
    @Autowired
    private TAuthService tAuthService;
    @Autowired
    private TFundService tFundService;

    @Override
    public ResultVO addUser(TUser user) {
        // 判断数据库中是否存在同样的名字？
        TUserVO userTemp = tUserDao.findByUsername(user.getUsername());
        if(userTemp != null){
            throw new AuthorizeException(CommonConstant.FAILED, "该账户名已经存在");
        }
        tUserDao.addUser(user);
        log.info("创建一个新的用户， id 为 ： " + user.getId());
        // 添加一个新的资产账户
        tFundService.createFundForUser(user.getId());
        return ResultVOUtil.success();
    }

    @Override
    public TUserVO findUserVOById(Integer id) {
        return tUserDao.findUserVOById(id);
    }

    @Override
    @Transactional
    public ResultVO updatePW(String oldPassword, String newPassword, HttpServletRequest request) {
        tAuthService.validatePassword(oldPassword, request);
        TUserVO userInfo = tAuthService.getUserInfo(request);
        log.info("username : " + userInfo.getUsername() + "修改密码为: " + newPassword);
        String pwDb = CommonUtil.MD5(newPassword);
        // 更新数据库
        updatePassword(userInfo.getId(), pwDb);

        // 更新token缓存
        userInfo.setPassword(newPassword);
        TokenManageUtil.put(request.getHeader(TokenConstant.TOKEN), userInfo);
        return ResultVOUtil.success();
    }

    @Override
    public boolean updatePassword(Integer id, String password) {
        return tUserDao.updatePassword(id, password);
    }

    @Override
    @Transactional
    public ResultVO modifyAntName(HttpServletRequest request, String entName) {
        TUserVO userVO = tAuthService.getUserInfo(request);
        tUserDao.modifyAntName(userVO.getId(), entName);

        userVO.setEntname(entName);
        TokenManageUtil.put(request.getHeader(TokenConstant.TOKEN), userVO);
        log.info("username : " + userVO.getUsername() + "修改昵称为: " + entName);

        return ResultVOUtil.success(userVO);
    }



}
