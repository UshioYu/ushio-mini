package com.hogwarts.ushio.controller;

import com.hogwarts.ushio.dto.TestDto;
import com.hogwarts.ushio.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @author: ushio
 * @description:
 **/
@RestController
@RequestMapping("ushio")
@Api(tags = "ushio用户测试管理")
@Slf4j
public class TestController {

    @Value("${ushio.key1}")
    private String ushioKey;

    @Autowired
    private TestService testService;

    @ApiOperation(value = "get方法",notes = "get")
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public String get(){
        return "泥壕";
    }

    @ApiOperation(value = "post方法",notes = "post")
    @RequestMapping(value = "/post",method = RequestMethod.POST)
    public String post(@RequestBody TestDto testDto){
        return "泥壕:" + testDto.getUserName() + "," + testDto.getPwd();
    }

    @ApiOperation(value = "getById方法",notes = "getById")
    @RequestMapping(value = "/byId/{id}/{subId}",method = RequestMethod.GET)
    public String getById(@PathVariable("id") Long id,@PathVariable("subId") Long subId){
        return "泥壕:" + id+","+subId;
    }

    @ApiOperation(value = "getByParam方法",notes = "getByParam")
    @GetMapping(value = "/byId")
    public String getByParam(@RequestParam("id") Long id,@RequestParam("subId") Long subId){
        return "泥壕:" + id+","+subId;
    }

    @ApiOperation(value = "post1方法",notes = "post1")
    @PostMapping("/post1")
    public String post1(@RequestBody TestDto testDto){
        log.info(ushioKey);
        return testService.login(testDto);
    }

    @ApiOperation(value = "put方法",notes = "put")
    @PutMapping("/put")
    public String put(){
        return "泥壕PUT";
    }

    @ApiOperation(value = "del方法",notes = "del")
    @DeleteMapping("/del")
    public String delete(){
        return "泥壕DEL";
    }
}
