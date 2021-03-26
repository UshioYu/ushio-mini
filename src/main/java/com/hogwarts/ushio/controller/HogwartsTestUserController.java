package com.hogwarts.ushio.controller;

import com.alibaba.fastjson.JSONObject;
import com.hogwarts.ushio.dto.ResultDto;
import com.hogwarts.ushio.dto.user.AddUserDto;
import com.hogwarts.ushio.entity.HogwartsTestUser;
import com.hogwarts.ushio.service.HogwartsTestUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author: ushio
 * @description:
 **/
@RestController
@RequestMapping("ushio")
@Api(tags = "芬兰学院-用户管理模块")
@Slf4j
public class HogwartsTestUserController {

    @Autowired
    private HogwartsTestUserService userService;

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public ResultDto<HogwartsTestUser> register(@RequestBody AddUserDto addUserDto){
        HogwartsTestUser testUser = new HogwartsTestUser();
        //把原对象的属性全部赋值给另一个对象，spring提供的方法
        BeanUtils.copyProperties(addUserDto,testUser);
        if (StringUtils.isEmpty(testUser.getUserName())) {
            return ResultDto.fail("用户名不能为空！");
        } else if (StringUtils.isEmpty(testUser.getPassword())) {
            return ResultDto.fail("密码不能为空！");
        }
        log.info("register testUser:" + JSONObject.toJSONString(testUser));
        return userService.register(testUser);
    }

    @ApiOperation(value = "用户删除")
    @DeleteMapping("delete/{userId}")
    public ResultDto<HogwartsTestUser> delete(@PathVariable("userId")Integer userId){

        if (userId <= 0) {
            return ResultDto.fail("用户id不能小于0");
        }

        log.info("delete userId：" + userId);
        return userService.delete(userId);
    }
}
