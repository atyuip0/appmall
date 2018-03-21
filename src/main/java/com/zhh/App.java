package com.zhh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * 启动
 * @author zhang.haihe
 */
@SpringBootApplication
@EntityScan(basePackageClasses = {Jsr310JpaConverters.class,App.class})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
