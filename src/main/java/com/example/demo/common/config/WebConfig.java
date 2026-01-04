package com.example.demo.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebConfig {


    /**
     * PasswordEncoder 빈으로 등록
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
// Create an encoder with strength 16
//    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
//    String result = encoder.encode("myPassword");
//    assertTrue(encoder.matches("myPassword", result));
