package com.kamalova.leisurepass.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.kamalova.leisurepass.api.controller"))
                .build()
                .useDefaultResponseMessages(false)
                .enableUrlTemplating(false)
                .apiInfo(apiEndPointsInfo());
    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("Leisure Pass Management REST API")
                .description("Leisure Pass Group Coding Challenge")
                .contact(new Contact("Irina Kamalova", "https://github.com/irenkamalova", "irenkamalova@gmail.com"))
                .version("1.0.0")
                .build();
    }
}