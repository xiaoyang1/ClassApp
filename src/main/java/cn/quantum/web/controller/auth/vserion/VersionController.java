package cn.quantum.web.controller.auth.vserion;

import cn.quantum.annotation.BusinessLog;
import cn.quantum.constant.VersionConstant;
import cn.quantum.util.ResultVOUtil;
import cn.quantum.web.vo.ResultVO;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tversion")
@BusinessLog("版本控制")
@Slf4j
public class VersionController {
    @Autowired
    private VersionConstant versionConstant;

    @RequestMapping("/getLastVersion")
    public ResultVO getLastVersion(){
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(versionConstant);
        return ResultVOUtil.success(jsonObject);
    }
}
