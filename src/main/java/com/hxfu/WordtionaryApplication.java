package com.hxfu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.hxfu.mapper")
@EnableSwagger2
public class WordtionaryApplication {

    public static void main(String[] args) {
        SpringApplication.run(WordtionaryApplication.class, args);
    }

}
