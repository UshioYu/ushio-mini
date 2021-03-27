package com.hogwarts.ushio.exception.global;

import com.hogwarts.ushio.dto.ResultDto;
import com.hogwarts.ushio.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author: ushio
 * @description:
 **/
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    //@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = ServiceException.class)
    public ResultDto serviceExceptionHandler(ServiceException exception){
        log.error(exception.getMessage());
        return resultFormat(exception);
    }

    @ExceptionHandler(value = Exception.class)
    public ResultDto exceptionHandler(Exception exception){
        log.error(exception.getMessage());
        return resultFormat(exception);
    }

    @ExceptionHandler(value = Throwable.class)
    public ResultDto throwableHandler(Throwable throwable){
        log.error(throwable.getMessage());
        return resultFormat(throwable);
    }

    public ResultDto resultFormat(Throwable throwable){
        String errorMsg = "系统错误";
        if (throwable instanceof ServiceException) {
            errorMsg = "业务异常" + "," + throwable.getMessage();
        } else if (throwable instanceof Exception) {
            errorMsg = "非业务异常";
        }
        return ResultDto.fail(errorMsg);
    }
}
