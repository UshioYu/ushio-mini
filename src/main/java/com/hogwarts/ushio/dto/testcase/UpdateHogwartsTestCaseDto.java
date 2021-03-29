package com.hogwarts.ushio.dto.testcase;

import com.hogwarts.ushio.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: ushio
 * @description:
 **/
@ApiModel(value="修改测试用例对象")
@Data
public class UpdateHogwartsTestCaseDto extends BaseDto {

    /**
     * 主键
     */
    @ApiModelProperty(value="测试用例主键",required=true)
    private Integer id;

    /**
     * 测试用例数据
     */
    @ApiModelProperty(value="测试用例数据",required=true)
    private String caseData;

    /**
     * 用例名称
     */
    @ApiModelProperty(value="测试用例名称",required=true)
    private String caseName;

    /**
     * 备注
     */
    @ApiModelProperty(value="测试用例备注")
    private String remark;
}
