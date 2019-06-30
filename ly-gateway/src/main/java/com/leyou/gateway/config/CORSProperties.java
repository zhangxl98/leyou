package com.leyou.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 6/30/19 1:46 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 加载跨域请求配置
 */
@Data
@ConfigurationProperties(prefix = "ly.cors")
public class CORSProperties {

    /**
     * 允许的域
     */
    private List<String> allowedOrigins;

    /**
     * 是否发送 cookie 信息
     */
    private Boolean allowCredentials;

    /**
     * 允许的请求方式
     */
    private List<String> allowedMethods;

    /**
     * 头信息
     */
    private List<String> allowedHeaders;

    /**
     * 有效时长，单位是秒
     */
    private Long maxAge;

    /**
     * 拦截路径
     */
    private String filterPath;
}
