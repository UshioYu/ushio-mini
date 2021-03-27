package com.hogwarts.ushio.service.impl;

import com.hogwarts.ushio.common.db.TokenDb;
import com.hogwarts.ushio.dao.HogwartsTestUserMapper;
import com.hogwarts.ushio.dto.ResultDto;
import com.hogwarts.ushio.dto.TokenDto;
import com.hogwarts.ushio.dto.user.LoginUserDto;
import com.hogwarts.ushio.entity.HogwartsTestUser;
import com.hogwarts.ushio.service.HogwartsTestUserService;
import com.hogwarts.ushio.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author: ushio
 * @description:
 **/
@Service
@Slf4j
public class HogwartsTestUserServiceImpl implements HogwartsTestUserService {

    @Autowired
    private HogwartsTestUserMapper userMapper;

    @Autowired
    private TokenDb tokenDb;

    @Override
    public ResultDto<HogwartsTestUser> register(HogwartsTestUser testUser) {

        String userName = testUser.getUserName();
        String password = testUser.getPassword();

        HogwartsTestUser hogwartsTestUser = new HogwartsTestUser();
        hogwartsTestUser.setUserName(userName);

        List<HogwartsTestUser> testUserList = userMapper.select(hogwartsTestUser);
        if (!Objects.isNull(testUserList) && testUserList.size() > 0) {
            return ResultDto.fail("用户已存在！");
        }
        String newPassword = DigestUtils.md5DigestAsHex((Constant.md5Hex_sign + userName + password).getBytes());
        testUser.setPassword(newPassword);
        testUser.setCreateTime(new Date());
        testUser.setUpdateTime(new Date());
        //userMapper.insertSelective(testUser);
        userMapper.insertUseGeneratedKeys(testUser);
        //置空，不让外部看到密码数据
        testUser.setPassword(null);
        return ResultDto.success("成功！",testUser);
    }

    @Override
    public ResultDto<String> login(String userName,String password) {
        String newPassword = DigestUtils.md5DigestAsHex((Constant.md5Hex_sign + userName + password).getBytes());

        HogwartsTestUser testUser = new HogwartsTestUser();
        testUser.setUserName(userName);
        testUser.setPassword(newPassword);
        //从数据库中查询此用户是否存在
        HogwartsTestUser newTestUser = userMapper.selectOne(testUser);
        if(Objects.isNull(newTestUser)){
            return ResultDto.fail("用户不存在或密码错误！");
        }
        String newToken = DigestUtils.md5DigestAsHex((System.currentTimeMillis() + Constant.md5Hex_sign + userName + password).getBytes());
        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(newToken);
        tokenDto.setUserId(newTestUser.getId());
        tokenDto.setUserName(userName);
        tokenDto.setDefaultJenkinsId(newTestUser.getDefaultJenkinsId());
        tokenDb.addUserInfo(newToken,tokenDto);
        return ResultDto.success("登录成功！", newToken);
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

    @Override
    public ResultDto<HogwartsTestUser> update(HogwartsTestUser testUser) {
        testUser.setUpdateTime(new Date());
        //userMapper.updateByPrimaryKeySelective(testUser);
        userMapper.updateUser(testUser.getUserName(),testUser.getPassword(), testUser.getId());
        return ResultDto.success("成功",testUser);
    }

    @Override
    public ResultDto<List<HogwartsTestUser>> getByName(HogwartsTestUser testUser) {
        //List<HogwartsTestUser> hogwartsTestUserList = userMapper.getByName(testUser.getUserName(), testUser.getId());
        List<HogwartsTestUser> hogwartsTestUserList = userMapper.select(testUser);
        return ResultDto.success("成功", hogwartsTestUserList);
    }
}
