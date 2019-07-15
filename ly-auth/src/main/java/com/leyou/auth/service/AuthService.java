package com.leyou.auth.service;

import com.leyou.auth.config.JwtProperties;
import com.leyou.common.auth.entity.UserInfo;
import com.leyou.common.auth.utils.JwtUtils;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.BeanHelper;
import com.leyou.common.utils.CookieUtils;
import com.leyou.user.client.UserClient;
import com.leyou.user.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/15/19 5:23 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 授权中心业务层
 */
@Service
public class AuthService {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private UserClient userClient;

    private static final String USER_ROLE = "guest";

    /**
     * 用户登录逻辑
     * <pre>createTime:
     * 7/15/19 6:56 PM</pre>
     *
     * @param username 用户名
     * @param password 密码
     * @param response response 对象
     */
    public void login(String username, String password, HttpServletResponse response) {

        try {
            // 获取用户数据
            UserDTO userDTO = userClient.queryByUsernameAndPassword(username, password);

            // 生成 UserInfo
            UserInfo userInfo = BeanHelper.copyProperties(userDTO, UserInfo.class);
            userInfo.setRole(USER_ROLE);

            // 生成 token
            String token = JwtUtils.generateTokenExpireInMinutes(userInfo, jwtProperties.getPrivateKey(), jwtProperties.getUser().getExpire());

            // 把 token 保存到 cookie
            CookieUtils.newBuilder()
                    .response(response)
                    .name(jwtProperties.getUser().getCookieName())
                    .value(token)
                    .domain(jwtProperties.getUser().getCookieDomain())
                    .httpOnly(true)
                    .maxAge(jwtProperties.getUser().getExpire() * 60)
                    .build();
        } catch (Exception e) {
            throw new LyException(ExceptionEnum.USER_NOT_FOUND);
        }
    }
}
