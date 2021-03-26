package com.hogwarts.ushio.service.impl;

import com.hogwarts.ushio.dao.HogwartsTestUserMapper;
import com.hogwarts.ushio.dto.ResultDto;
import com.hogwarts.ushio.entity.HogwartsTestUser;
import com.hogwarts.ushio.service.HogwartsTestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author: ushio
 * @description:
 **/
@Service
public class HogwartsTestUserServiceImpl implements HogwartsTestUserService {

    @Autowired
    private HogwartsTestUserMapper userMapper;

    @Override
    public ResultDto<HogwartsTestUser> register(HogwartsTestUser testUser) {
        testUser.setCreateTime(new Date());
        testUser.setUpdateTime(new Date());
        //userMapper.insertSelective(testUser);
        userMapper.insertUseGeneratedKeys(testUser);
        return ResultDto.success("成功！",testUser);
    }

    @Override
    public ResultDto<HogwartsTestUser> delete(Integer userId) {
        //这里删除还有其他方法，根据自己的业务需要进行调整即可
        //userMapper.deleteByIds(""+userId);
        HogwartsTestUser testUser = new HogwartsTestUser();
        testUser.setId(userId);
        userMapper.delete(testUser);
        return ResultDto.success("删除成功！");
    }
}
