package com.hogwarts.ushio.controller;

import com.alibaba.fastjson.JSONObject;
import com.hogwarts.ushio.common.db.TokenDb;
import com.hogwarts.ushio.dto.BuildDto;
import com.hogwarts.ushio.dto.ResultDto;
import com.hogwarts.ushio.dto.TokenDto;
import com.hogwarts.ushio.dto.user.AddUserDto;
import com.hogwarts.ushio.dto.user.LoginUserDto;
import com.hogwarts.ushio.entity.HogwartsTestUser;
import com.hogwarts.ushio.service.HogwartsTestUserService;
import com.hogwarts.ushio.utils.Constant;
import com.hogwarts.ushio.utils.JenkinsUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.TextUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

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

    @Autowired
    private TokenDb tokenDb;

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

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public ResultDto<String> login(@RequestBody LoginUserDto loginUserDto){
        String userName = loginUserDto.getUserName();
        String password = loginUserDto.getPassword();
        log.info("userName:" + userName + ",password:" + password);
        if (TextUtils.isEmpty(userName)) {
            return ResultDto.fail("用户名不能为空！");
        } else if (TextUtils.isEmpty(password)) {
            return ResultDto.fail("密码不能为空！");
        }
        return userService.login(userName,password);
    }

    @ApiOperation(value = "修改用户信息")
    @PutMapping("/update")
    public ResultDto<HogwartsTestUser> update(@RequestBody LoginUserDto loginUserDto){
        HogwartsTestUser testUser = new HogwartsTestUser();
        //把原对象的属性全部赋值给另一个对象，spring提供的方法
        BeanUtils.copyProperties(loginUserDto,testUser);
        if (StringUtils.isEmpty(testUser.getUserName())) {
            return ResultDto.fail("用户名不能为空！");
        } else if (StringUtils.isEmpty(testUser.getPassword())) {
            return ResultDto.fail("密码不能为空！");
        }
        log.info("update testUser:" + JSONObject.toJSONString(testUser));
        return userService.update(testUser);
    }

    @ApiOperation(value = "根据用户名模糊查询")
    @GetMapping("byName")
    public ResultDto<List<HogwartsTestUser>> getByName(@RequestParam(value = "userId", required = false) Integer userId, @RequestParam(value = "userName", required = false) String userName) {
        HogwartsTestUser testUser = new HogwartsTestUser();
        testUser.setId(userId);
        testUser.setUserName(userName);
        log.info("getByName testUser:" + JSONObject.toJSONString(testUser));
        return userService.getByName(testUser);
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

    @ApiOperation(value = "用户登出")
    @DeleteMapping("logout")
    public ResultDto logout(HttpServletRequest request){

        String token = request.getHeader(Constant.LOGIN_TOKEN);
        log.info("logout token：" + token);
        boolean isLogined =tokenDb.isOnline(token);
        log.info("logout isLogined：" + isLogined);
        if(!isLogined){
            return ResultDto.fail("用户未登录！");
        }

        TokenDto tokenDto = tokenDb.removeUserInfo(token);
        return ResultDto.success("登出成功！",tokenDto);
    }

    @ApiOperation(value = "是否已经登录接口")
    @GetMapping("isLogin")
    public ResultDto<TokenDto> isLogin(HttpServletRequest request){

        String token = request.getHeader(Constant.LOGIN_TOKEN);
        log.info("logout token：" + token);
        boolean isLogined =tokenDb.isOnline(token);
        log.info("logout isLogined：" + isLogined);
        if(!isLogined){
            return ResultDto.fail("用户未登录！");
        }

        TokenDto tokenDto = tokenDb.getUserInfo(token);
        return ResultDto.success("已经登录！",tokenDto);
    }

    @ApiOperation("调用jenkins构建job")
    @PutMapping("build")
    public ResultDto build(@RequestBody BuildDto buildDto) throws IOException, URISyntaxException {
        log.info("build buildDto:"+JSONObject.toJSONString(buildDto));
        JenkinsUtil.build(buildDto.getJobName(), buildDto.getUserId(), buildDto.getRemark(), buildDto.getTestCommand());
        return ResultDto.success("构建成功！！");
    }
}
