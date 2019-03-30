package cn.quantum.web.controller.activity;

import cn.quantum.annotation.BusinessLog;
import cn.quantum.util.BonusPoolUtil;
import cn.quantum.util.ResultVOUtil;
import cn.quantum.web.service.TAuthService;
import cn.quantum.web.service.TFundService;
import cn.quantum.web.vo.ResultVO;
import cn.quantum.web.vo.TUserVO;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 这个类主要是用来完成运动打卡任务的
 */
@RestController
@RequestMapping("/tfund")
@BusinessLog("资产管理")
@Slf4j
public class TFundController {

    @Autowired
    private TAuthService tAuthService;
    @Autowired
    private TFundService tFundService;

    /**
     *  获得投资信息
     * @param request
     * @return
     */
    @RequestMapping("/getInvestation")
    public ResultVO getInvestation(HttpServletRequest request){
        TUserVO userVO = tAuthService.getUserInfo(request);
        return tFundService.getInvestation(userVO.getId());
    }

    /**
     *  获得收入信息
     * @param request
     * @return
     */
    @RequestMapping("/getIncome")
    public ResultVO getIncome(HttpServletRequest request){
        TUserVO userVO = tAuthService.getUserInfo(request);
        return tFundService.getIncome(userVO.getId());
    }


    @RequestMapping("/getBonusInfo")
    public ResultVO hetBonusInfo(HttpServletRequest request){
        if(BonusPoolUtil.isOpen){
            JSONObject bonus = new JSONObject();
            bonus.put("numOfPeopleToDevide", BonusPoolUtil.getNumOfPeopleToDevide());
            bonus.put("totalBonus", BonusPoolUtil.getTotalBonus());
            return ResultVOUtil.success(bonus);
        } else {
            return ResultVOUtil.error(0, "客观别急，今日收益还没结算~");
        }
    }

}
