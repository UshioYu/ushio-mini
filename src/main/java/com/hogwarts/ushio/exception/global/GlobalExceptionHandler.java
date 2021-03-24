package com.hogwarts.ushio.exception.global;

import com.hogwarts.ushio.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.rmi.ServerException;

/**
 * @author: ushio
 * @description:
 **/
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = ServiceException.class)
    public String serviceExceptionHandler(ServiceException exception){
        log.error(exception.getMessage());
        return resultFormat(exception);
    }

    @ExceptionHandler(value = Exception.class)
    public String exceptionHandler(Exception exception){
        log.error(exception.getMessage());
        return resultFormat(exception);
    }

    @ExceptionHandler(value = Throwable.class)
    public String throwableHandler(Throwable throwable){
        log.error(throwable.getMessage());
        return resultFormat(throwable);
    }

    public String resultFormat(Throwable throwable){
        String errorMsg = "系统错误";
        if (throwable instanceof ServiceException) {
            errorMsg = "业务异常";
        } else if (throwable instanceof Exception) {
            errorMsg = "非业务异常";
        }
        return errorMsg;
    }
}
