package com.hogwarts.ushio.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: ushio
 * @description:
 **/
@Data
@ApiModel(value = "ushio测试类",description = "ushio测试类描述")
public class TestDto {
    @ApiModelProperty(value = "用户名",example = "ushio",required = true)
    private String userName;
    @ApiModelProperty(value = "密码",example = "ushioyujian",required = true)
    private String pwd;
}
