package com.leyou.auth.service;

import com.leyou.auth.config.JwtProperties;
import com.leyou.auth.entity.ApplicationInfo;
import com.leyou.auth.mapper.ApplicationInfoMapper;
import com.leyou.common.auth.entity.AppInfo;
import com.leyou.common.auth.entity.Payload;
import com.leyou.common.auth.entity.UserInfo;
import com.leyou.common.auth.utils.JwtUtils;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.BeanHelper;
import com.leyou.common.utils.CookieUtils;
import com.leyou.user.client.UserClient;
import com.leyou.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ApplicationInfoMapper applicationInfoMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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

            // 生成 token 并存入 Cookie
            buildTokenCookie(response, userInfo);

        } catch (Exception e) {
            throw new LyException(ExceptionEnum.USER_NOT_FOUND);
        }
    }

    /**
     * 验证用户信息，并重新生成 token，保持用户的登录状态
     * <pre>createTime:
     * 7/15/19 9:06 PM</pre>
     *
     * @param request  请求
     * @param response 响应
     * @return 用户信息
     */
    public UserInfo verifyUser(HttpServletRequest request, HttpServletResponse response) {

        try {
            // 获取 token
            String oldToken = CookieUtils.getCookieValue(request, jwtProperties.getUser().getCookieName());

            // 解析 token，获取载荷信息
            Payload<UserInfo> oldPayload = JwtUtils.getInfoFromToken(oldToken, jwtProperties.getPublicKey(), UserInfo.class);

            // 获取 tokenId
            String oldTokenId = oldPayload.getId();
            // redis 中存在 tokenId，这用户已注销登录状态
            if (redisTemplate.hasKey(oldTokenId)) {
                throw new LyException(ExceptionEnum.VERIFY_FAIL);
            }

            // 生成新的 token 并存入 Cookie
            buildTokenCookie(response, oldPayload.getInfo());

            // 返回用户信息
            return oldPayload.getInfo();
        } catch (Exception e) {

            throw new LyException(ExceptionEnum.VERIFY_FAIL);
        }
    }

    /**
     * 用户注销
     * <pre>createTime:
     * 7/15/19 9:23 PM</pre>
     *
     * @param request  请求
     * @param response 响应
     */
    public void logout(HttpServletRequest request, HttpServletResponse response) {

        try {

            // 获取 token
            String token = CookieUtils.getCookieValue(request, jwtProperties.getUser().getCookieName());
            // 解析 token，得到 tokenId 和 过期时间
            Payload<UserInfo> payload = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey(), UserInfo.class);
            String tokenId = payload.getId();
            Date expiration = payload.getExpiration();

            // 到期剩余时间大于 5 秒
            if (System.currentTimeMillis() + 5000 < expiration.getTime()) {
                // 保存 tokenId 到 redis 中，过期自动销毁
                redisTemplate.opsForValue().set(tokenId, "", expiration.getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
            }

            // 覆盖原来的 Cookie
            Cookie cookie = new Cookie(jwtProperties.getUser().getCookieName(), "");
            cookie.setDomain(jwtProperties.getUser().getCookieDomain());
            cookie.setPath("/");
            cookie.setMaxAge(0);

            response.addCookie(cookie);
        } catch (Exception e) {
            throw new LyException(ExceptionEnum.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 生成 token，并存入 Cookie
     * <pre>createTime:
     * 7/15/19 9:54 PM</pre>
     *
     * @param response 响应对象
     * @param userInfo 用户信息
     */
    private void buildTokenCookie(HttpServletResponse response, UserInfo userInfo) {
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
    }

    /**
     * 微服务认证并申请令牌
     * <pre>createTime:
     * 7/21/19 3:22 PM</pre>
     *
     * @param id     服务id
     * @param secret 密码
     * @return token
     */
    public String authenticate(Long id, String secret) {

        // 校验 id 和 secret
        ApplicationInfo applicationInfo = applicationInfoMapper.selectByPrimaryKey(id);
        // 判断是否为空
        if (applicationInfo == null) {
            // id不存在，抛出异常
            throw new LyException(ExceptionEnum.INVALID_SERVER_ID_SECRET);
        }
        // 验密
        if (!bCryptPasswordEncoder.matches(secret, applicationInfo.getSecret())) {
            // 密码错误
            throw new LyException(ExceptionEnum.INVALID_SERVER_ID_SECRET);
        }

        // 查询服务权限信息
        List<Long> idList = applicationInfoMapper.queryTargetIdList(id);

        // 封装 AppInfo 载荷
        AppInfo appInfo = AppInfo.builder()
                .id(id)
                .serviceName(applicationInfo.getServiceName())
                .targetList(idList)
                .build();

        // 生成 token，并返回
        return JwtUtils.generateTokenExpireInMinutes(
                appInfo, jwtProperties.getPrivateKey(), jwtProperties.getApp().getExpire());
    }
}
