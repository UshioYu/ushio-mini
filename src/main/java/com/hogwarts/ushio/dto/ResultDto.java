package com.hogwarts.ushio.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author: ushio
 * @description:响应返回类
 **/
@ApiModel(value = "基础返回类",description = "基础返回类")
public class ResultDto<T> implements Serializable {

    private static final long serialVersionUID = -8470542434051466936L;

    @ApiModelProperty(value = "返回结果码，1成功，0失败", required = true, example = "1", allowableValues = "1,2")
    private Integer resultCode;
    @ApiModelProperty(value = "提示信息",example = "成功",allowableValues = "成功，失败")
    private String message;
    @ApiModelProperty(value = "响应结果信息")
    private T data;

    public static ResultDto newInstance(){
        return new ResultDto();
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message == null ? "" : message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setAsSuccess(){
        this.resultCode = 1;
    }

    public void setAsFail(){
        this.resultCode = 0;
    }

    public static ResultDto fail(String message){
        ResultDto resultDto = new ResultDto();
        resultDto.setAsFail();
        resultDto.setMessage(message);
        return resultDto;
    }

    public static <T> ResultDto<T> fail(String message,T data){
        ResultDto<T> resultDto = new ResultDto();
        resultDto.setAsFail();
        resultDto.setMessage(message);
        resultDto.setData(data);
        return resultDto;
    }

    public static ResultDto success(String message){
        ResultDto resultDto = new ResultDto();
        resultDto.setAsSuccess();
        resultDto.setMessage(message);
        return resultDto;
    }

    public static <T> ResultDto<T> success(String message,T data){
        ResultDto<T> resultDto = new ResultDto();
        resultDto.setAsSuccess();
        resultDto.setMessage(message);
        resultDto.setData(data);
        return resultDto;
    }

}
