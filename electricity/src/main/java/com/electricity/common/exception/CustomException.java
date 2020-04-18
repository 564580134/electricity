package com.electricity.common.exception;

import com.electricity.common.enums.ExceptionEnum;
import com.electricity.common.enums.ResponseEnum;
import lombok.Getter;


/**
 * @Description: 自定义异常处理
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/

@Getter
public class CustomException extends RuntimeException {

    public Integer code;

    public CustomException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
    }

    public CustomException(String message) {
        super(message);
        this.code = ResponseEnum.EXCEPTION.getCode();
    }
}
