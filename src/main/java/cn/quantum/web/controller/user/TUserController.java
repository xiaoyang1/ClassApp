package cn.quantum.web.controller.user;

import cn.quantum.annotation.BusinessLog;
import cn.quantum.util.CommonUtil;
import cn.quantum.util.ResultVOUtil;
import cn.quantum.web.db.domain.TUser;
import cn.quantum.web.service.TAuthService;
import cn.quantum.web.service.TUserService;
import cn.quantum.web.vo.ResultVO;
import cn.quantum.web.vo.TUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/tuser")
@BusinessLog("用户管理")
@Slf4j
public class TUserController {

    @Autowired
    private TUserService tUserService;
    @Autowired
    private TAuthService tAuthService;



    /**
     * 修改用户密码
     *
     * @param oldPassword
     * @param newPassword
     * @param request header需要token
     * @return
     */
    @PostMapping("/updatePassword")
    @BusinessLog("修改密码")
    public ResultVO updatePW(String oldPassword, String newPassword, HttpServletRequest request) {
        return tUserService.updatePW(oldPassword, newPassword, request);
    }


    /**
     * 修改用户昵称
     *
     * @param entName
     * @return
     */
    @PostMapping(value = "/modifyUserAntName")
    @BusinessLog("修改用户昵称")
    public ResultVO modifyUser(HttpServletRequest request, String entName){
        if(StringUtils.isEmpty(entName)){
            return ResultVOUtil.error(0, "昵称不能为空");
        }

        return tUserService.modifyAntName(request, entName);
    }


}
