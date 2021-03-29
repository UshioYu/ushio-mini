package com.hogwarts.ushio.dao;

import com.hogwarts.ushio.common.mapper.MySqlExtensionMapper;
import com.hogwarts.ushio.dto.jenkins.QueryHogwartsTestJenkinsListDto;
import com.hogwarts.ushio.entity.HogwartsTestJenkins;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HogwartsTestJenkinsMapper extends MySqlExtensionMapper<HogwartsTestJenkins> {

    int count(@Param("param") QueryHogwartsTestJenkinsListDto param);

    List<HogwartsTestJenkins> list(@Param("param") QueryHogwartsTestJenkinsListDto param, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
}