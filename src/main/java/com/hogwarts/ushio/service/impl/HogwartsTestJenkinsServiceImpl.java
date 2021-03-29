package com.hogwarts.ushio.service.impl;

import com.hogwarts.ushio.common.db.TokenDb;
import com.hogwarts.ushio.dao.HogwartsTestJenkinsMapper;
import com.hogwarts.ushio.dao.HogwartsTestUserMapper;
import com.hogwarts.ushio.dto.PageTableRequest;
import com.hogwarts.ushio.dto.PageTableResponse;
import com.hogwarts.ushio.dto.ResultDto;
import com.hogwarts.ushio.dto.TokenDto;
import com.hogwarts.ushio.dto.jenkins.QueryHogwartsTestJenkinsListDto;
import com.hogwarts.ushio.entity.HogwartsTestJenkins;
import com.hogwarts.ushio.entity.HogwartsTestUser;
import com.hogwarts.ushio.service.HogwartsTestJenkinsService;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class HogwartsTestJenkinsServiceImpl implements HogwartsTestJenkinsService {

    @Autowired
    private HogwartsTestJenkinsMapper jenkinsMapper;

    @Autowired
    private HogwartsTestUserMapper userMapper;

    @Autowired
    private TokenDb tokenDb;

    @Override
    public ResultDto<HogwartsTestJenkins> save(TokenDto tokenDto, HogwartsTestJenkins hogwartsTestJenkins) {
        hogwartsTestJenkins.setCreateTime(new Date());
        hogwartsTestJenkins.setUpdateTime(new Date());
        jenkinsMapper.insertUseGeneratedKeys(hogwartsTestJenkins);
        Integer defaultJenkinsFlag = hogwartsTestJenkins.getDefaultJenkinsFlag();
        if (Objects.nonNull(defaultJenkinsFlag) && defaultJenkinsFlag == 1) {
            //勾选了默认，需要把user表的defaultJenkinId更新赋值
            HogwartsTestUser queryTestUser = new HogwartsTestUser();
            queryTestUser.setId(tokenDto.getUserId());
            HogwartsTestUser resultTestUser = userMapper.selectOne(queryTestUser);
            resultTestUser.setDefaultJenkinsId(hogwartsTestJenkins.getId());
            userMapper.updateByPrimaryKeySelective(resultTestUser);
            tokenDto.setDefaultJenkinsId(hogwartsTestJenkins.getId());
            tokenDb.addUserInfo(tokenDto.getToken(),tokenDto);
        }
        return ResultDto.success("成功", hogwartsTestJenkins);
    }

    @Override
    public ResultDto<HogwartsTestJenkins> delete(Integer jenkinsId, TokenDto tokenDto) {
        HogwartsTestJenkins queryTestJenkins = new HogwartsTestJenkins();
        queryTestJenkins.setId(jenkinsId);
        queryTestJenkins.setCreateUserId(tokenDto.getUserId());
        HogwartsTestJenkins resultTestJenkins = jenkinsMapper.selectOne(queryTestJenkins);
        if(Objects.isNull(resultTestJenkins)){
            return ResultDto.fail("jenkins信息不存在！");
        }
        HogwartsTestUser queryTestUser = new HogwartsTestUser();
        queryTestUser.setId(tokenDto.getUserId());
        HogwartsTestUser resultTestUser = userMapper.selectOne(queryTestUser);
        if(jenkinsId == resultTestUser.getDefaultJenkinsId()){
            //是默认的jenkinsId,需要同步修改user表的数据
            queryTestUser.setDefaultJenkinsId(null);
            userMapper.updateByPrimaryKeySelective(queryTestUser);
            tokenDto.setDefaultJenkinsId(null);
            tokenDb.addUserInfo(tokenDto.getToken(),tokenDto);
        }

        jenkinsMapper.deleteByPrimaryKey(jenkinsId);
        return ResultDto.success("删除成功！");
    }

    @Override
    public ResultDto<HogwartsTestJenkins> update(TokenDto tokenDto, HogwartsTestJenkins hogwartsTestJenkins) {
        HogwartsTestJenkins queryTestJenkins = new HogwartsTestJenkins();
        queryTestJenkins.setCreateUserId(hogwartsTestJenkins.getCreateUserId());
        queryTestJenkins.setId(hogwartsTestJenkins.getId());
        HogwartsTestJenkins resultTestJenkins = jenkinsMapper.selectOne(queryTestJenkins);
        if(Objects.isNull(resultTestJenkins)){
            return  ResultDto.fail("未查询到jenkins信息!");
        }

        hogwartsTestJenkins.setCreateTime(resultTestJenkins.getCreateTime());
        hogwartsTestJenkins.setUpdateTime(new Date());
        jenkinsMapper.updateByPrimaryKey(hogwartsTestJenkins);

        Integer defaultJenkinsFlag = hogwartsTestJenkins.getDefaultJenkinsFlag();
        if (Objects.nonNull(defaultJenkinsFlag) && defaultJenkinsFlag == 1) {
            //勾选了默认，需要把user表的defaultJenkinId更新赋值
            HogwartsTestUser queryTestUser = new HogwartsTestUser();
            queryTestUser.setId(tokenDto.getUserId());
            queryTestUser.setDefaultJenkinsId(hogwartsTestJenkins.getId());
            userMapper.updateByPrimaryKeySelective(queryTestUser);

            tokenDto.setDefaultJenkinsId(hogwartsTestJenkins.getId());
            tokenDb.addUserInfo(tokenDto.getToken(),tokenDto);
        }
        return ResultDto.success("更新成功", hogwartsTestJenkins);
    }

    @Override
    public ResultDto<HogwartsTestJenkins> getById(Integer jenkinsId, TokenDto tokenDto) {
        HogwartsTestJenkins queryTestJenkins = new HogwartsTestJenkins();
        queryTestJenkins.setId(jenkinsId);
        queryTestJenkins.setCreateUserId(tokenDto.getUserId());
        HogwartsTestJenkins resultTestJenkins = jenkinsMapper.selectOne(queryTestJenkins);
        if(Objects.isNull(resultTestJenkins)){
            return ResultDto.fail("jenkins信息不存在！");
        }
        HogwartsTestUser queryTestUser = new HogwartsTestUser();
        queryTestUser.setId(tokenDto.getUserId());
        HogwartsTestUser resultTestUser = userMapper.selectOne(queryTestUser);
        if(jenkinsId == resultTestUser.getDefaultJenkinsId()){
            //是默认的jenkinsId
            resultTestJenkins.setDefaultJenkinsFlag(1);
        }
        return ResultDto.success("查询成功！",resultTestJenkins);
    }

    @Override
    public ResultDto<PageTableResponse<HogwartsTestJenkins>> list(PageTableRequest<QueryHogwartsTestJenkinsListDto> pageTableRequest) {
        QueryHogwartsTestJenkinsListDto queryParam = pageTableRequest.getParams();
        Integer pageNum = pageTableRequest.getPageNum();
        Integer pageSize = pageTableRequest.getPageSize();
        Integer createUserId = queryParam.getCreateUserId();
        //先通过createUserId进行查询
        HogwartsTestUser queryTestUser = new HogwartsTestUser();
        queryTestUser.setId(createUserId);
        HogwartsTestUser resultTestUser = userMapper.selectOne(queryTestUser);
        Integer defaultJenkinsId = resultTestUser.getDefaultJenkinsId();
        //总数
        Integer count = jenkinsMapper.count(queryParam);
        //查询每页的数据
        List<HogwartsTestJenkins> lists = jenkinsMapper.list(queryParam, (pageNum - 1) * pageSize, pageSize);
        //根据用户表默认Jenkins为列表数据赋值
        for (HogwartsTestJenkins hogwartsTestJenkins :lists) {
            if (defaultJenkinsId.equals(hogwartsTestJenkins.getId())) {
                hogwartsTestJenkins.setDefaultJenkinsFlag(1);
            }
        }
        PageTableResponse<HogwartsTestJenkins> pageTableResponse = new PageTableResponse<>();
        pageTableResponse.setData(lists);
        pageTableResponse.setRecordsTotal(count);
        return ResultDto.success("分页查询成功！", pageTableResponse);
    }
}
