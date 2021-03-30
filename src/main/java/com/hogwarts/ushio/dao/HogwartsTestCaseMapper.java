package com.hogwarts.ushio.dao;

import com.hogwarts.ushio.common.mapper.MySqlExtensionMapper;
import com.hogwarts.ushio.dto.testcase.QueryHogwartsTestCaseDto;
import com.hogwarts.ushio.entity.HogwartsTestCase;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HogwartsTestCaseMapper extends MySqlExtensionMapper<HogwartsTestCase> {

    int count(@Param("param") QueryHogwartsTestCaseDto param);

    List<HogwartsTestCase> list(@Param("param") QueryHogwartsTestCaseDto param, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
}