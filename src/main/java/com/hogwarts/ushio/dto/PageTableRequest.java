package com.hogwarts.ushio.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: ushio
 * @description:分页查询参数
 **/
@Data
@ApiModel(value = "列表查询的分页参数",description = "请求参数")
public class PageTableRequest<Dto extends BaseDto> implements Serializable {

    private static final long serialVersionUID = 218463839366913379L;

    @ApiModelProperty(value = "页码",example = "1",required = true)
    private Integer pageNum =1;
    @ApiModelProperty(value = "每页数量",example = "10",required = true)
    private Integer pageSize = 10;
    @ApiModelProperty(value = "请求参数",example = "status=1",required = true)
    private Dto params;

}
