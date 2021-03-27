package com.hogwarts.ushio.dto;

import lombok.Data;

/**
 * @author: ushio
 * @description:
 **/
@Data
public class TokenDto {

    private Integer userId;
    private String userName;
    private Integer defaultJenkinsId;
    private String token;
}
