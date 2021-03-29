package com.hogwarts.ushio.service;

import com.hogwarts.ushio.dto.PageTableRequest;
import com.hogwarts.ushio.dto.PageTableResponse;
import com.hogwarts.ushio.dto.ResultDto;
import com.hogwarts.ushio.dto.TokenDto;
import com.hogwarts.ushio.dto.jenkins.QueryHogwartsTestJenkinsListDto;
import com.hogwarts.ushio.entity.HogwartsTestJenkins;

public interface HogwartsTestJenkinsService {

    ResultDto<HogwartsTestJenkins> save(TokenDto tokenDto,HogwartsTestJenkins hogwartsTestJenkins);

    ResultDto<HogwartsTestJenkins> delete(Integer jenkinsId,TokenDto tokenDto);

    ResultDto<HogwartsTestJenkins> update(TokenDto tokenDto,HogwartsTestJenkins hogwartsTestJenkins);

    ResultDto<HogwartsTestJenkins> getById(Integer jenkinsId,TokenDto tokenDto);

    ResultDto<PageTableResponse<HogwartsTestJenkins>> list(PageTableRequest<QueryHogwartsTestJenkinsListDto> pageTableRequest);
}
