package com.pck;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.pck.mapper")
public class PCKBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(PCKBlogApplication.class, args);
    }
}
