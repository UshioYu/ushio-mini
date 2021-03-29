package com.hogwarts.ushio.dao;

import com.hogwarts.ushio.common.mapper.MySqlExtensionMapper;
import com.hogwarts.ushio.entity.HogwartsTestCase;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface HogwartsTestCaseMapper extends MySqlExtensionMapper<HogwartsTestCase> {
}