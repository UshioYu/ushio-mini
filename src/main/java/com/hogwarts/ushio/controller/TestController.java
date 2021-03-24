package com.hogwarts.ushio.controller;

import com.hogwarts.ushio.dto.TestDto;
import com.hogwarts.ushio.service.TestService;
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
@Slf4j
public class TestController {

    @Value("${ushio.key1}")
    private String ushioKey;

    @Autowired
    private TestService testService;

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public String get(){
        return "泥壕";
    }

    @RequestMapping(value = "/post",method = RequestMethod.POST)
    public String post(@RequestBody TestDto testDto){
        return "泥壕:" + testDto.getUserName() + "," + testDto.getPwd();
    }

    @RequestMapping(value = "/byId/{id}/{subId}",method = RequestMethod.GET)
    public String getById(@PathVariable("id") Long id,@PathVariable("subId") Long subId){
        return "泥壕:" + id+","+subId;
    }

    @GetMapping(value = "/byId")
    public String getByParam(@RequestParam("id") Long id,@RequestParam("subId") Long subId){
        return "泥壕:" + id+","+subId;
    }

    @PostMapping("/post1")
    public String post1(@RequestBody TestDto testDto){
        log.info(ushioKey);
        return testService.login(testDto);
    }

    @PutMapping("/put")
    public String put(){
        return "泥壕PUT";
    }

    @DeleteMapping("/del")
    public String delete(){
        return "泥壕DEL";
    }
}
