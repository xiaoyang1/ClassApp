package cn.quantum.exception;

import cn.quantum.enums.ResultEnum;
import lombok.Data;

@Data
public class AuthorizeException extends RuntimeException {
    private Integer code;

    public AuthorizeException(Integer code, ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = code;
    }

    public AuthorizeException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
