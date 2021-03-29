package com.hogwarts.ushio.service;

import com.hogwarts.ushio.dto.PageTableRequest;
import com.hogwarts.ushio.dto.PageTableResponse;
import com.hogwarts.ushio.dto.ResultDto;
import com.hogwarts.ushio.dto.testcase.QueryHogwartsTestCaseDto;
import com.hogwarts.ushio.entity.HogwartsTestCase;

public interface HogwartsTestCaseService {

    ResultDto<HogwartsTestCase> add(HogwartsTestCase hogwartsTestCase);

    ResultDto<HogwartsTestCase> delete(Integer caseId,Integer createUserId);

    ResultDto<HogwartsTestCase> update(HogwartsTestCase hogwartsTestCase);

    ResultDto<HogwartsTestCase> getById(Integer caseId,Integer createUserId);

    ResultDto<PageTableResponse<HogwartsTestCase>> list(PageTableRequest<QueryHogwartsTestCaseDto> pageTableRequest);

    //根据用户id和caseId查询case原始数据-直接返回字符串，因为会保存为文件
    String getCaseDataById(Integer caseId,Integer createUserId);
}
