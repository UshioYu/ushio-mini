package com.hogwarts.ushio.exception;

/**
 * @author: ushio
 * @description:
 **/
public class ServiceException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private String message;

    @Override
    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message == null ? "" : message;
    }

    public ServiceException(String message){
        this.message = message;
    }

    public ServiceException(String message,Throwable throwable){
        super(message,throwable);
        this.message = message;
    }

    public static void throwException(String message){
        throw new ServiceException(message);
    }
}
