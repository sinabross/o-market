package com.omarket;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

import static com.omarket.global.constant.Timezone.LocalTimezone;

@SpringBootApplication
public class OMarketApplication {

    @PostConstruct
    public void setUp(){
        TimeZone.setDefault(TimeZone.getTimeZone(LocalTimezone));
    }

    public static void main(String[] args) {
        SpringApplication.run(OMarketApplication.class, args);
    }

}
