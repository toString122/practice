package com.liwj.securitydemo1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@MapperScan("com.liwj.securitydemo1.mapper")
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
public class Securitydemo1Application {

    public static void main(String[] args) {
        SpringApplication.run(Securitydemo1Application.class, args);
    }

}
