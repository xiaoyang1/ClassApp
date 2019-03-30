package cn.quantum.util;

import cn.quantum.constant.CommonConstant;
import cn.quantum.web.vo.ResultVO;

/**
 * 生成结果对象
 */
public class ResultVOUtil {

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(CommonConstant.SUCCESS);
        resultVO.setMsg("成功");
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

    public static ResultVO error(String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(CommonConstant.FAILED);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
