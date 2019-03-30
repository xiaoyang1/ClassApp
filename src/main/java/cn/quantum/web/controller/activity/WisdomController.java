package cn.quantum.web.controller.activity;

import cn.quantum.annotation.BusinessLog;
import cn.quantum.util.ResultVOUtil;
import cn.quantum.web.db.domain.Wisdom;
import cn.quantum.web.service.WisdomService;
import cn.quantum.web.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 这个类主要是用来完成名言的管理任务的
 */
@RestController
@RequestMapping("/twisdom")
@BusinessLog("名言管理")
@Slf4j
public class WisdomController {

    @Autowired
    private WisdomService wisdomService;

    @PostMapping("/addWisdom")
    public ResultVO addWisdom(String wisdom, String url){
        wisdomService.insertOne(wisdom, url);
        return ResultVOUtil.success();
    }

    @RequestMapping("getNewest")
    public ResultVO getNewest(){
        Wisdom newest = wisdomService.findLast();
        if(newest != null){
            return ResultVOUtil.success(newest);
        } else {
            return ResultVOUtil.error("获取失败！");
        }
    }
}
