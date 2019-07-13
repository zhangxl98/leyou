package com.leyou.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/13/19 7:35 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 配置随机密钥
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ly.encoder.crypt")
public class PasswordConfig {

    private int strength;
    private String secret;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // 利用密钥生成随机安全码
        SecureRandom secureRandom = new SecureRandom(secret.getBytes());
        // 初始化BCryptPasswordEncoder
        return new BCryptPasswordEncoder(strength, secureRandom);
    }
}
