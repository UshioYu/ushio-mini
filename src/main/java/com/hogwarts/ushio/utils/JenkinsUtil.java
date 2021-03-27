package com.hogwarts.ushio.utils;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.offbytwo.jenkins.model.Job;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: ushio
 * @description:
 **/
@Slf4j
public class JenkinsUtil {

    public static void build(String jobName, String userId, String remark,String testCommand) throws IOException, URISyntaxException {
        ClassPathResource classPathResource = new ClassPathResource("jenkinsConfigDir/hogwarts_test_jenkins_show.xml");
        InputStream inputStream = classPathResource.getInputStream();

        String jobConfigXml = FileUtil.getText(inputStream);

        String baseUrl = "http://www.ushio.fun:8001/";
        String userName = "admin";
        String token = "118f1d6afd0afed734367f3b416558f9a3";

//        JenkinsHttpClient jenkinsHttpClient = new JenkinsHttpClient();
//        String jenkinsVersion = jenkinsHttpClient.getJenkinsVersion();
//
//        log.info("jenkinsVersion== "+ jenkinsVersion);

        JenkinsServer jenkinsServer = new JenkinsServer(new URI(baseUrl),userName,token);

        log.info("updateJob jobConfigXml:"+ jobConfigXml);
        //这里暂且假设job已经存在，等后面平台整合时再通过job判断到底是先newJob还是updateJob
        jenkinsServer.updateJob(jobName,jobConfigXml,true);

        log.info("updateJob1");
        Map<String, Job> jobMap = jenkinsServer.getJobs();

        Job job = jobMap.get(jobName);

        Map<String,String> map = new HashMap<>();
        map.put("userId",userId);
        map.put("remark",remark);
        map.put("testCommand",testCommand);

        log.info("job.build");
        job.build(map,true);

        log.info("job.build finish!!");
    }
}
