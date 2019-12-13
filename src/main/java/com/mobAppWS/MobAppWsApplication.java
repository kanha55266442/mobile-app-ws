package com.mobAppWS;

import com.mobAppWS.ui.SpringApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MobAppWsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MobAppWsApplication.class, args);
    }
    @Bean
    public SpringApplicationContext springApplicationContext() {
        return new SpringApplicationContext();
    }
}
