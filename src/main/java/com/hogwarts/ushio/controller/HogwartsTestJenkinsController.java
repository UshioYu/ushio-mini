package com.hogwarts.ushio.controller;

import com.alibaba.fastjson.JSONObject;
import com.hogwarts.ushio.common.db.TokenDb;
import com.hogwarts.ushio.dto.PageTableRequest;
import com.hogwarts.ushio.dto.PageTableResponse;
import com.hogwarts.ushio.dto.ResultDto;
import com.hogwarts.ushio.dto.TokenDto;
import com.hogwarts.ushio.dto.jenkins.AddHogwartsTestJenkinsDto;
import com.hogwarts.ushio.dto.jenkins.QueryHogwartsTestJenkinsListDto;
import com.hogwarts.ushio.dto.jenkins.UpdateHogwartsTestJenkinsDto;
import com.hogwarts.ushio.entity.HogwartsTestJenkins;
import com.hogwarts.ushio.service.HogwartsTestJenkinsService;
import com.hogwarts.ushio.utils.Constant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.TextUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author: ushio
 * @description:
 **/
@RestController
@RequestMapping("jenkins")
@Api(tags = "芬兰学院-jenkins管理模块")
@Slf4j
public class HogwartsTestJenkinsController {

    @Autowired
    private HogwartsTestJenkinsService jenkinsService;

    @Autowired
    private TokenDb tokenDb;

    @ApiOperation(value = "新增jenkins")
    @PostMapping("/add")
    public ResultDto<HogwartsTestJenkins> save(HttpServletRequest request, @RequestBody AddHogwartsTestJenkinsDto addHogwartsTestJenkinsDto){
        log.info("jenkins-save addHogwartsTestJenkinsDto:"+ JSONObject.toJSONString(addHogwartsTestJenkinsDto));
        String token = request.getHeader(Constant.LOGIN_TOKEN);
        log.info("jenkins-save token:" + token);
        TokenDto tokenDto = tokenDb.getUserInfo(token);
        log.info("jenkins-save tokenDto:" + tokenDto);
        //避免有人输错类型，类型选择后，不用加.
        String commandRunCaseSuffix = addHogwartsTestJenkinsDto.getCommandRunCaseSuffix();
        if (Objects.nonNull(commandRunCaseSuffix) && commandRunCaseSuffix.startsWith(".")) {
            commandRunCaseSuffix = commandRunCaseSuffix.replace(".", "");
        }
        addHogwartsTestJenkinsDto.setCommandRunCaseSuffix(commandRunCaseSuffix);

        HogwartsTestJenkins testJenkins = new HogwartsTestJenkins();
        BeanUtils.copyProperties(addHogwartsTestJenkinsDto,testJenkins);
        testJenkins.setCreateUserId(tokenDto.getUserId());
        return jenkinsService.save(tokenDto,testJenkins);
    }

    @ApiOperation(value = "修改jenkins信息")
    @PutMapping("/update")
    public ResultDto<HogwartsTestJenkins> update(HttpServletRequest request, @RequestBody UpdateHogwartsTestJenkinsDto updateHogwartsTestJenkinsDto){
        log.info("jenkins-update updateHogwartsTestJenkinsDto:"+JSONObject.toJSONString(updateHogwartsTestJenkinsDto));
        Integer jenkinsId = updateHogwartsTestJenkinsDto.getId();
        String name = updateHogwartsTestJenkinsDto.getName();
        //todo ushio 这里其他有些必填字段也可以加入描述
        if(Objects.isNull(jenkinsId)){
            return ResultDto.fail("JenkinsId不能为空");
        }

        if(StringUtils.isEmpty(name)){
            return ResultDto.fail("Jenkins名称不能为空");
        }

        String token = request.getHeader(Constant.LOGIN_TOKEN);
        log.info("jenkins-update token:" + token);
        TokenDto tokenDto = tokenDb.getUserInfo(token);
        log.info("jenkins-update tokenDto:" + tokenDto);
        //避免有人输错类型，类型选择后，不用加.
        String commandRunCaseSuffix = updateHogwartsTestJenkinsDto.getCommandRunCaseSuffix();
        if (Objects.nonNull(commandRunCaseSuffix) && commandRunCaseSuffix.startsWith(".")) {
            commandRunCaseSuffix = commandRunCaseSuffix.replace(".", "");
        }
        updateHogwartsTestJenkinsDto.setCommandRunCaseSuffix(commandRunCaseSuffix);

        HogwartsTestJenkins testJenkins = new HogwartsTestJenkins();
        BeanUtils.copyProperties(updateHogwartsTestJenkinsDto,testJenkins);
        testJenkins.setCreateUserId(tokenDto.getUserId());
        return jenkinsService.update(tokenDto,testJenkins);
    }

    @ApiOperation(value = "删除jenkins信息")
    @DeleteMapping("/delete/{jenkinsId}")
    public ResultDto<HogwartsTestJenkins> delete(HttpServletRequest request, @PathVariable("jenkinsId") Integer jenkinsId){
        log.info("jenkins-delete jenkinsId:"+jenkinsId);
        if(Objects.isNull(jenkinsId)){
            return  ResultDto.fail("jenkins的id不能为空！");
        }

        String token = request.getHeader(Constant.LOGIN_TOKEN);
        log.info("jenkins-delete token:" + token);
        TokenDto tokenDto = tokenDb.getUserInfo(token);
        log.info("jenkins-delete tokenDto:" + tokenDto);
        return jenkinsService.delete(jenkinsId, tokenDto);
    }

    @ApiOperation(value = "查询jenkins信息")
    @GetMapping("/getById/{jenkinsId}")
    public ResultDto<HogwartsTestJenkins> getById(HttpServletRequest request, @PathVariable("jenkinsId") Integer jenkinsId){
        log.info("jenkins-getById jenkinsId:"+jenkinsId);
        if(Objects.isNull(jenkinsId)){
            return  ResultDto.fail("jenkins的id不能为空！");
        }

        String token = request.getHeader(Constant.LOGIN_TOKEN);
        log.info("jenkins-getById token:" + token);
        TokenDto tokenDto = tokenDb.getUserInfo(token);
        log.info("jenkins-getById tokenDto:" + tokenDto);
        return jenkinsService.getById(jenkinsId, tokenDto);

    }

    @ApiOperation(value = "列表查询")
    @GetMapping("/list")
    public ResultDto<PageTableResponse<HogwartsTestJenkins>> list(HttpServletRequest request, PageTableRequest<QueryHogwartsTestJenkinsListDto> pageTableRequest) {
        log.info("jenkins-list");
        if(Objects.isNull(pageTableRequest)){
            return  ResultDto.fail("pageTableRequest不能为空！");
        }

        String token = request.getHeader(Constant.LOGIN_TOKEN);
        log.info("jenkins-getById token:" + token);
        TokenDto tokenDto = tokenDb.getUserInfo(token);
        log.info("jenkins-getById tokenDto:" + tokenDto);

        QueryHogwartsTestJenkinsListDto queryHogwartsTestJenkinsListDto = pageTableRequest.getParams();
        //如果参数为空，把userId传参
        if (Objects.isNull(queryHogwartsTestJenkinsListDto)) {
            queryHogwartsTestJenkinsListDto = new QueryHogwartsTestJenkinsListDto();
            queryHogwartsTestJenkinsListDto.setCreateUserId(tokenDto.getUserId());
            pageTableRequest.setParams(queryHogwartsTestJenkinsListDto);
        }
        return jenkinsService.list(pageTableRequest);
    }

}
