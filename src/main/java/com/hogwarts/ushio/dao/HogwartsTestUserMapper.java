package com.hogwarts.ushio.dao;

import com.hogwarts.ushio.common.mapper.MySqlExtensionMapper;
import com.hogwarts.ushio.entity.HogwartsTestUser;
import org.springframework.stereotype.Repository;

@Repository
public interface HogwartsTestUserMapper extends MySqlExtensionMapper<HogwartsTestUser> {
}