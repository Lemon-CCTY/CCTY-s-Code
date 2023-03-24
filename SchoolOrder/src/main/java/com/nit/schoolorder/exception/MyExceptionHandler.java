package com.nit.schoolorder.exception;

/**
 * @Author: CCTY
 * @Date: 2023/2/20 21:59
 * 解决业务逻辑上的错误
 **/


import com.nit.schoolorder.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


//@RestControllerAdvice=@ControllerAdvice+@ResponseBody
@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public Result errorHandler(ServiceException se) {
        return Result.error(se.getCode(), se.getMessage());
    }

}
