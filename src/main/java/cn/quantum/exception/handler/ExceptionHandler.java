package cn.quantum.exception.handler;

import cn.quantum.exception.AuthorizeException;
import cn.quantum.util.ResultVOUtil;
import cn.quantum.web.vo.ResultVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandler {
    /**
     * 用户认证相关异常
     *
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = AuthorizeException.class)
    @ResponseBody
    public ResultVO handlerSellerException(AuthorizeException e) {
        // 可能会有其他的特殊处理，先暂时直接返回错误格式的json
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }
}
