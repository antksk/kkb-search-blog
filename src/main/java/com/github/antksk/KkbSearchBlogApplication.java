package com.github.antksk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
public class KkbSearchBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(KkbSearchBlogApplication.class, args);
    }

}
