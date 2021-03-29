package com.hogwarts.ushio.dto.jenkins;

import com.hogwarts.ushio.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: ushio
 * @description:
 **/
@ApiModel(value="查询Jenkins信息列表对象")
@Data
public class QueryHogwartsTestJenkinsListDto extends BaseDto {

    @ApiModelProperty(value="Jenkins名称")
    private String name;

    @ApiModelProperty(value="创建者id(客户端传值无效，以token数据为准)")
    private Integer createUserId;

}
