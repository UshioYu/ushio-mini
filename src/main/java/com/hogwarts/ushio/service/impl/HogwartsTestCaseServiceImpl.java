package com.hogwarts.ushio.service.impl;

import com.hogwarts.ushio.dao.HogwartsTestCaseMapper;
import com.hogwarts.ushio.dto.PageTableRequest;
import com.hogwarts.ushio.dto.PageTableResponse;
import com.hogwarts.ushio.dto.ResultDto;
import com.hogwarts.ushio.dto.testcase.QueryHogwartsTestCaseDto;
import com.hogwarts.ushio.entity.HogwartsTestCase;
import com.hogwarts.ushio.service.HogwartsTestCaseService;
import com.hogwarts.ushio.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author: ushio
 * @description:
 **/
@Service
public class HogwartsTestCaseServiceImpl implements HogwartsTestCaseService {

    @Autowired
    private HogwartsTestCaseMapper caseMapper;

    @Override
    public ResultDto<HogwartsTestCase> add(HogwartsTestCase hogwartsTestCase) {
        hogwartsTestCase.setCreateTime(new Date());
        hogwartsTestCase.setUpdateTime(new Date());
        hogwartsTestCase.setDelFlag(Constant.DEL_FLAG_ONE);
        caseMapper.insertUseGeneratedKeys(hogwartsTestCase);
        return ResultDto.success("添加成功！");
    }

    @Override
    public ResultDto<HogwartsTestCase> delete(Integer caseId, Integer createUserId) {
        return null;
    }

    @Override
    public ResultDto<HogwartsTestCase> update(HogwartsTestCase hogwartsTestCase) {
        return null;
    }

    @Override
    public ResultDto<HogwartsTestCase> getById(Integer caseId, Integer createUserId) {
        return null;
    }

    @Override
    public ResultDto<PageTableResponse<HogwartsTestCase>> list(PageTableRequest<QueryHogwartsTestCaseDto> pageTableRequest) {
        return null;
    }

    @Override
    public String getCaseDataById(Integer caseId, Integer createUserId) {
        return null;
    }
}
