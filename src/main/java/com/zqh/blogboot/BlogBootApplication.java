package com.zqh.blogboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BlogBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogBootApplication.class, args);
    }

}

