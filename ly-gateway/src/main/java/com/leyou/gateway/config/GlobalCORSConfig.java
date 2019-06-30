package com.leyou.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 6/29/19 8:54 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description CORS 跨域过滤器
 */
@Configuration
@EnableConfigurationProperties(CORSProperties.class)
public class GlobalCORSConfig {

    @Autowired
    private CORSProperties properties;

    @Bean
    public CorsFilter corsFilter() {

        // 1.添加 CORS 配置信息
        CorsConfiguration config = new CorsConfiguration();

        // 1.1 允许的域，不能写 * ,否则 cookie 无法使用
        properties.getAllowedOrigins().forEach(config::addAllowedOrigin);

        // 1.2 是否发送 cookie 信息
        config.setAllowCredentials(properties.getAllowCredentials());

        // 1.3 允许的请求方式
        properties.getAllowedMethods().forEach(config::addAllowedMethod);

        // 1.4 添加头信息
        properties.getAllowedHeaders().forEach(config::addAllowedHeader);

        // 2.添加映射路径,拦截一切请求
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration(properties.getFilterPath(), config);

        // 3.返回新的 CORSFilter
        return new CorsFilter(configSource);
    }
}
