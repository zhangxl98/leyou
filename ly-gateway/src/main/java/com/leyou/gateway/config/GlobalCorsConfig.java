package com.leyou.gateway.config;

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
public class GlobalCorsConfig {

    @Bean
    public CorsFilter corsFilter() {

        // 1.添加 CORS 配置信息
        CorsConfiguration config = new CorsConfiguration();

        // 1.1 允许的域，不能写 * ,否则 cookie 无法使用
        config.addAllowedOrigin("http://manage.leyou.com");

        // 1.2 是否发送 cookie 信息
        config.setAllowCredentials(true);

        // 1.3 允许的请求方式
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");

        // 1.4 添加头信息
        config.addAllowedHeader("*");

        // 2.添加映射路径,拦截一切请求
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);

        // 3.返回新的 CORSFilter
        return new CorsFilter(configSource);
    }
}
