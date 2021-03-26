package com.hogwarts.ushio.dto.user;

import com.hogwarts.ushio.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: ushio
 * @description:
 **/
@Data
@ApiModel(value = "用户对象",description = "用户user")
public class LoginUserDto extends BaseDto {

    @ApiModelProperty(value = "用户名",required = true)
    private String userName;
    @ApiModelProperty(value = "密码",required = true)
    private String password;

}
