package com.tuling.tulingmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author roy
 * @desc
 */

@SpringBootApplication
@EnableFeignClients
@MapperScan({"com.tuling.tulingmall.open.mapper"})
public class TulingmallOpenApplication {
    public static void main(String[] args) {
        SpringApplication.run(TulingmallOpenApplication.class,args);
    }
}
