package com.rancho.web.api.config;

import com.google.common.collect.Lists;
import com.rancho.web.common.result.ResultCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


/**
 * swagger2配置
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket tokenApi(){
        List<ResponseMessage> responseMessageList = new ArrayList<>();
        for(ResultCode resultCodes: ResultCode.values()){
            responseMessageList.add(new ResponseMessageBuilder().code((int)resultCodes.getCode()).message(resultCodes.getMessage()).build());
        }
        return new Docket(DocumentationType.SWAGGER_2)
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .globalResponseMessage(RequestMethod.POST, responseMessageList)
                .globalResponseMessage(RequestMethod.PUT, responseMessageList)
                .globalResponseMessage(RequestMethod.DELETE, responseMessageList)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rancho.web.api.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Lists.newArrayList(apiKey()));

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("web-api接口")
                .description("web-api接口")
                .version("1.0")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "Header");
    }

}
