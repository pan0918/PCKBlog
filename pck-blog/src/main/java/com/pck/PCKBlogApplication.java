package com.pck;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.pck.mapper")
@EnableScheduling // 定时器启动
@EnableSwagger2
public class PCKBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(PCKBlogApplication.class, args);
    }
}
