package cn.quantum.web.controller.user;

import cn.quantum.annotation.BusinessLog;
import cn.quantum.util.ClassCacheUtil;
import cn.quantum.util.CommonUtil;
import cn.quantum.util.ResultVOUtil;
import cn.quantum.web.db.domain.Schedule;
import cn.quantum.web.service.ScheduleService;
import cn.quantum.web.service.TAuthService;
import cn.quantum.web.vo.ResultVO;
import cn.quantum.web.vo.TUserVO;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tclass")
@BusinessLog("课程表管理")
@Slf4j
public class TScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private TAuthService tAuthService;

    @RequestMapping("/insertClass")
    public ResultVO insertClass(HttpServletRequest request, String classSchedule){
        TUserVO userVO = tAuthService.getUserInfo(request);
        Schedule schedule = scheduleService.getScheduleById(userVO.getId());
        if(StringUtils.isEmpty(classSchedule)){
            return ResultVOUtil.error(0, "课程表不能为空！");
        }
        if(schedule != null){
            return ResultVOUtil.error(0, "用户已经存在课程表，请不要多次创建");
        }
        try {
            JSONObject clasz = JSONObject.parseObject(classSchedule);
            schedule = new Schedule(userVO.getId(), clasz.toJSONString());
            scheduleService.insertSchedule(schedule);
            return ResultVOUtil.success();
        } catch (Exception e){
            return ResultVOUtil.error(0, "上传课程表 JSON 格式有误！");
        }
    }

    @RequestMapping("/updateClass")
    public ResultVO updateClass(HttpServletRequest request, String classSchedule){
        TUserVO userVO = tAuthService.getUserInfo(request);
        if(StringUtils.isEmpty(classSchedule)){
            return ResultVOUtil.error(0, "课程表不能为空！");
        }
        try{
            JSONObject clasz = JSONObject.parseObject(classSchedule);
            return scheduleService.updateSchedule(userVO.getId(), clasz.toJSONString());
        } catch (Exception e){
            e.printStackTrace();
            return ResultVOUtil.error(0, "上传课程表 JSON 格式有误！");
        }
    }

    @RequestMapping("/deleteClass")
    public ResultVO deleteClass(HttpServletRequest request){
        TUserVO userVO = tAuthService.getUserInfo(request);
        scheduleService.deleteById(userVO.getId());
        return ResultVOUtil.success();
    }

    @RequestMapping("/getClass")
    public ResultVO getClass(HttpServletRequest request){
        TUserVO userVO = tAuthService.getUserInfo(request);
        Schedule schedule = scheduleService.getScheduleById(userVO.getId());
        if(schedule == null){
            return ResultVOUtil.error(0, "该用户不存在课表信息~");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("class", schedule.getSchedule());
        jsonObject.put("lastMod", schedule.getLastModTime());
        return ResultVOUtil.success(jsonObject);
    }

    @RequestMapping("/shareClass")
    public ResultVO shareClass(HttpServletRequest request){
        TUserVO userVO = tAuthService.getUserInfo(request);

        // 想内存添加共享课程id
        Schedule schedule = scheduleService.getScheduleById(userVO.getId());
        if(schedule == null){
            return ResultVOUtil.error(0, "该用户不存在课表信息~");
        }
        String id = userVO.getId() + "";
        String key = id + CommonUtil.MD5(CommonUtil.createGuid()).substring(0, 6-id.length());
        Integer scheduleId = schedule.getUserId();
        ClassCacheUtil.put(key, scheduleId);
        log.info("用户 " + userVO.getId() + "开启了课程分享， key " + key + " !");

        JSONObject result = new JSONObject();
        result.put("shareKey", key);
        return ResultVOUtil.success(result);
    }

    @RequestMapping("/getShareClass")
    public ResultVO getShareClass(HttpServletRequest request, String shareKey){
        TUserVO userVO = tAuthService.getUserInfo(request);

        Integer scheduleId = ClassCacheUtil.get(shareKey);
        if(scheduleId == null){
            return ResultVOUtil.error(0, "该课表分享已经失效，请重新分享~");
        }

        Schedule schedule = scheduleService.getScheduleById(scheduleId);
        if(schedule == null){
            // 课表可能已经被删除。
            return ResultVOUtil.error(0, "分享课表信息已经不存在~");
        }

        return ResultVOUtil.success(schedule.getSchedule());
    }


}
