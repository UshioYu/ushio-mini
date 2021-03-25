package com.hogwarts.ushio.service.impl;

import com.hogwarts.ushio.dto.ResultDto;
import com.hogwarts.ushio.dto.TestDto;
import com.hogwarts.ushio.service.TestService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author: ushio
 * @description:
 **/
@Service
//@Component
public class TestServiceImpl implements TestService {

    @Override
    public ResultDto login(TestDto testDto) {
        return ResultDto.success("success!", testDto);
    }

}
