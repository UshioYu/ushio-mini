package com.hogwarts.ushio.service;

import com.hogwarts.ushio.dto.ResultDto;
import com.hogwarts.ushio.dto.user.LoginUserDto;
import com.hogwarts.ushio.entity.HogwartsTestUser;

import java.util.List;

/**
 * @author: ushio
 * @description:
 **/
public interface HogwartsTestUserService {

    ResultDto<HogwartsTestUser> register(HogwartsTestUser testUser);
    ResultDto<String> login(String userName,String password);
    ResultDto<HogwartsTestUser> delete(Integer userId);
    ResultDto<HogwartsTestUser> update(HogwartsTestUser testUser);
    ResultDto<List<HogwartsTestUser>> getByName(HogwartsTestUser testUser);
 }
