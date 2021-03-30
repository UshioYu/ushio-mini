package com.hogwarts.ushio.service.impl;

import com.hogwarts.ushio.dao.HogwartsTestCaseMapper;
import com.hogwarts.ushio.dto.PageTableRequest;
import com.hogwarts.ushio.dto.PageTableResponse;
import com.hogwarts.ushio.dto.ResultDto;
import com.hogwarts.ushio.dto.testcase.QueryHogwartsTestCaseDto;
import com.hogwarts.ushio.entity.HogwartsTestCase;
import com.hogwarts.ushio.service.HogwartsTestCaseService;
import com.hogwarts.ushio.utils.Constant;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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
        HogwartsTestCase queryTestCase = new HogwartsTestCase();
        queryTestCase.setId(caseId);
        queryTestCase.setCreateUserId(createUserId);
        queryTestCase.setDelFlag(Constant.DEL_FLAG_ONE);
        HogwartsTestCase resultTestCase = caseMapper.selectOne(queryTestCase);
        if(Objects.isNull(resultTestCase)){
            return ResultDto.fail("未查询到测试用例信息！");
        }
        resultTestCase.setDelFlag(Constant.DEL_FLAG_ZERO);
        caseMapper.updateByPrimaryKey(resultTestCase);
        return ResultDto.success("删除成功！",resultTestCase);
    }

    @Override
    public ResultDto<HogwartsTestCase> update(HogwartsTestCase hogwartsTestCase) {
        HogwartsTestCase queryTestCase = new HogwartsTestCase();
        queryTestCase.setId(hogwartsTestCase.getId());
        queryTestCase.setCreateUserId(hogwartsTestCase.getCreateUserId());
        queryTestCase.setDelFlag(Constant.DEL_FLAG_ONE);
        HogwartsTestCase resultTestCase = caseMapper.selectOne(queryTestCase);
        if(Objects.isNull(resultTestCase)){
            return ResultDto.fail("未查询到测试用例信息！");
        }
        hogwartsTestCase.setCreateTime(resultTestCase.getCreateTime());
        hogwartsTestCase.setUpdateTime(new Date());
        hogwartsTestCase.setDelFlag(Constant.DEL_FLAG_ONE);
        return ResultDto.success("查询成功！",hogwartsTestCase);
    }

    @Override
    public ResultDto<HogwartsTestCase> getById(Integer caseId, Integer createUserId) {
        HogwartsTestCase queryTestCase = new HogwartsTestCase();
        queryTestCase.setId(caseId);
        queryTestCase.setCreateUserId(createUserId);
        queryTestCase.setDelFlag(Constant.DEL_FLAG_ONE);
        HogwartsTestCase resultTestCase = caseMapper.selectOne(queryTestCase);
        if(Objects.isNull(resultTestCase)){
            return ResultDto.fail("未查询到测试用例信息！");
        }
        return ResultDto.success("查询成功！",resultTestCase);
    }

    @Override
    public ResultDto<PageTableResponse<HogwartsTestCase>> list(PageTableRequest<QueryHogwartsTestCaseDto> pageTableRequest) {
        QueryHogwartsTestCaseDto queryParam = pageTableRequest.getParams();
        Integer pageNum = pageTableRequest.getPageNum();
        Integer pageSize = pageTableRequest.getPageSize();
        //总数
        Integer count = caseMapper.count(queryParam);
        //查询每页的数据
        List<HogwartsTestCase> lists = caseMapper.list(queryParam, (pageNum - 1) * pageSize, pageSize);
        PageTableResponse<HogwartsTestCase> pageTableResponse = new PageTableResponse<>();
        pageTableResponse.setData(lists);
        pageTableResponse.setRecordsTotal(count);
        return ResultDto.success("分页查询成功！", pageTableResponse);
    }

    @Override
    public String getCaseDataById(Integer caseId, Integer createUserId) {
        if (Objects.isNull(caseId)) {
            return "caseId不能为空！";
        }
        HogwartsTestCase queryTestCase = new HogwartsTestCase();
        queryTestCase.setId(caseId);
        queryTestCase.setCreateUserId(createUserId);
        queryTestCase.setDelFlag(Constant.DEL_FLAG_ONE);
        HogwartsTestCase resultTestCase = caseMapper.selectOne(queryTestCase);
        if (Objects.isNull(resultTestCase) ||
                TextUtils.isEmpty(resultTestCase.getCaseData())) {
            return "查询结果为空！";
        }
        return resultTestCase.getCaseData();
    }
}
