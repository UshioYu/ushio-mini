package com.hogwarts.ushio.dto.testcase;

import com.hogwarts.ushio.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: ushio
 * @description:
 **/
@ApiModel(value="查询测试用例信息列表对象")
@Data
public class QueryHogwartsTestCaseDto extends BaseDto {

    @ApiModelProperty(value="测试用例名称")
    private String caseName;

    @ApiModelProperty(value="创建者id(客户端传值无效，以token数据为准)")
    private Integer createUserId;

}
