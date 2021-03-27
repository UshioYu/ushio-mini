package com.hogwarts.ushio.dao;

import com.hogwarts.ushio.common.mapper.MySqlExtensionMapper;
import com.hogwarts.ushio.entity.HogwartsTestUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HogwartsTestUserMapper extends MySqlExtensionMapper<HogwartsTestUser> {

    int updateUser(@Param("userName") String userName, @Param("password") String password, @Param("id") Integer id);

    List<HogwartsTestUser> getByName(@Param("userName") String userName, @Param("id") Integer id);
}