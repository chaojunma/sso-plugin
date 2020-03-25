package com.mk.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSso
@SpringBootApplication
public class SsoServerApplication  {

    public static void main(String[] args) {
        SpringApplication.run(SsoServerApplication .class);
    }
}
