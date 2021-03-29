package com.hogwarts.ushio.controller;

import com.alibaba.fastjson.JSONObject;
import com.hogwarts.ushio.common.db.TokenDb;
import com.hogwarts.ushio.dto.ResultDto;
import com.hogwarts.ushio.dto.TokenDto;
import com.hogwarts.ushio.dto.testcase.AddHogwartsTestCaseDto;
import com.hogwarts.ushio.entity.HogwartsTestCase;
import com.hogwarts.ushio.service.HogwartsTestCaseService;
import com.hogwarts.ushio.utils.Constant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author: ushio
 * @description:
 **/
@RestController
@RequestMapping("testcase")
@Api(tags = "芬兰学院-用例管理模块")
@Slf4j
public class HogwartsTestCaseController {

    private static final String TAG = "HogwartsTestCaseController";

    @Autowired
    private HogwartsTestCaseService caseService;

    @Autowired
    private TokenDb tokenDb;

    @ApiOperation(value = "批量新增测试用例", notes="仅用于测试用户")
    @PostMapping("text")
    public ResultDto saveText(HttpServletRequest request, @RequestBody AddHogwartsTestCaseDto addHogwartsTestCaseDto){
        log.info(TAG + "saveText-addHogwartsTestCaseDto:" + JSONObject.toJSONString(addHogwartsTestCaseDto));
        if(Objects.isNull(addHogwartsTestCaseDto.getCaseName())){
            return ResultDto.fail("测试用例名称为空！");
        }

        if(Objects.isNull(addHogwartsTestCaseDto.getCaseData())){
            return ResultDto.fail("测试用例数据为空！");
        }

        HogwartsTestCase testCase = new HogwartsTestCase();
        BeanUtils.copyProperties(addHogwartsTestCaseDto,testCase);

        TokenDto tokenDto = tokenDb.getUserInfo(request.getHeader(Constant.LOGIN_TOKEN));
        testCase.setCreateUserId(tokenDto.getUserId());
        return caseService.add(testCase);
    }

    @ApiOperation(value = "文件上传", notes="仅用于测试用户")
    @PostMapping("file")
    public ResultDto saveFile(HttpServletRequest request,
                              @RequestParam("caseFile") MultipartFile caseFile
            , AddHogwartsTestCaseDto addHogwartsTestCaseDto) throws IOException {
        log.info(TAG + "saveFile-addHogwartsTestCaseDto:" + JSONObject.toJSONString(addHogwartsTestCaseDto));

        String name = caseFile.getOriginalFilename();

        log.info(TAG + "saveFile-name==  " + name);

        InputStream inputStream = caseFile.getInputStream();

        String caseData = IOUtils.toString(inputStream,"UTF-8");

        inputStream.close();


        log.info(TAG + "saveFile-caseData== " +caseData);

        ResultDto resultDto = ResultDto.success("成功", "caseData== "
                +caseData + "   getCaseName==  "+addHogwartsTestCaseDto.getCaseName());
        return resultDto;
    }

}
