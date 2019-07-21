package com.leyou.user.interceptor;

import com.leyou.common.auth.entity.AppInfo;
import com.leyou.common.auth.entity.Payload;
import com.leyou.common.auth.utils.JwtUtils;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.user.config.JwtProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/21/19 7:06 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description
 */
@Component
@EnableConfigurationProperties(JwtProperties.class)
@Slf4j
public class PrivilegeInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 能请求返回 true，不能请求返回 false
     * <pre>createTime:
     * 7/21/19 7:16 PM</pre>
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 获取token，解析token，成功，校验服务的合法性，一切正常放行
        try {

            // 从请求头中获取token
            String token = request.getHeader(jwtProperties.getApp().getHeaderName());

            // 解析token
            Payload<AppInfo> appInfoPayload = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey(), AppInfo.class);

            // 从载荷中获取封装的AppInfo
            AppInfo info = appInfoPayload.getInfo();

            // 获取原始请求的目标服务列表
            List<Long> targetList = info.getTargetList();

            // 如果当前服务id在列表中则放行，否则拦截（false）

            if (targetList.contains(jwtProperties.getApp().getId())) {
                return true;
            }
        } catch (Exception e) {
            log.error("服务访问被拒绝,token 认证失败!", e);
            throw new LyException(ExceptionEnum.INVALID_TOKEN);
        }

        return false;

    }
}
