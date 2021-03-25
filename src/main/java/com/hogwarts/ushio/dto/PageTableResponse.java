package com.hogwarts.ushio.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: ushio
 * @description:分页查询结果
 **/
@Data
public class PageTableResponse<T> implements Serializable {

    private static final long serialVersionUID = 4045711220727000498L;

    private Integer recordsTotal;
    private List<T> data;

}
