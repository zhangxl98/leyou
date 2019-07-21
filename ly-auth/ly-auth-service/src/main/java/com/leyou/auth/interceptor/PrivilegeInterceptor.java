package com.leyou.auth.interceptor;

import com.leyou.auth.config.JwtProperties;
import com.leyou.auth.task.PrivilegeTokenHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/21/19 8:30 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description
 */
@Component
@EnableConfigurationProperties(JwtProperties.class)
public class PrivilegeInterceptor implements RequestInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private PrivilegeTokenHolder tokenHolder;

    @Override
    public void apply(RequestTemplate template) {
        // 获取token
        String token = tokenHolder.getToken();
        // 给请求添加头信息
        template.header(jwtProperties.getApp().getHeaderName(), token);
    }
}
