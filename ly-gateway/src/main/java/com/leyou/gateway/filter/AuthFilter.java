package com.leyou.gateway.filter;

import com.leyou.common.auth.entity.UserInfo;
import com.leyou.common.auth.utils.JwtUtils;
import com.leyou.common.utils.CookieUtils;
import com.leyou.gateway.config.FilterProperties;
import com.leyou.gateway.config.JwtProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/18/19 7:12 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description
 */
@Component
@EnableConfigurationProperties({JwtProperties.class, FilterProperties.class})
@Slf4j
public class AuthFilter extends ZuulFilter {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private FilterProperties filterProperties;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        // 当前 Filter 优先于 FORM_BODY_WRAPPER_FILTER 执行
        return FilterConstants.FORM_BODY_WRAPPER_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {

        // 获取请求路径
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String requestURI = request.getRequestURI();

        // 获取请求白名单
        Set<String> allowPaths = filterProperties.getAllowPaths();

        // 遍历 allowPaths，如果请求路径在 白名单内则放行
        for (String allowPath : allowPaths) {
            if (requestURI.startsWith(allowPath)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext currentContext = RequestContext.getCurrentContext();

        // 校验 token，如果解析失败，则拦截
        HttpServletRequest request = currentContext.getRequest();

        try {
            String token = CookieUtils.getCookieValue(request, jwtProperties.getUser().getCookieName());

            JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey(), UserInfo.class);
        } catch (Exception e) {

            currentContext.setSendZuulResponse(false);

            currentContext.setResponseStatusCode(403);
        }
        return null;
    }
}
