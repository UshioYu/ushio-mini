package com.hogwarts.ushio.service;

import com.hogwarts.ushio.dto.ResultDto;
import com.hogwarts.ushio.entity.HogwartsTestUser;

/**
 * @author: ushio
 * @description:
 **/
public interface HogwartsTestUserService {

    ResultDto<HogwartsTestUser> register(HogwartsTestUser testUser);
    ResultDto<HogwartsTestUser> delete(Integer userId);
 }
