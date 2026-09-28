package com.its.base_java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EntityScan(basePackageClasses = {BaseJavaApplication.class, Jsr310JpaConverters.class})
@EnableScheduling
@EnableAsync
@SpringBootApplication
@ConfigurationPropertiesScan
public class BaseJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseJavaApplication.class, args);
    }

}
