package com.hogwarts.ushio.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: ushio
 * @description:
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        ParameterBuilder builder = new ParameterBuilder();
        builder.parameterType("header").name("token")
                .description("token值")
                .required(true)
                .modelRef(new ModelRef("string")); // 在swagger里显示header

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("ushio-mini")
                .globalOperationParameters(Lists.newArrayList(builder.build()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hogwarts.ushio.controller"))//限制只展示当前包下的接口
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("ushio-mini-system")
                .description("ushio-mini-doc")
                .contact(new Contact("ushio", "www.ushio.fun", "ushioyujian@gmail.com"))
                .version("1.0")
                .build();
    }
}
