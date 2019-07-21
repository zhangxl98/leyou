package com.leyou.auth.controller;

import com.leyou.auth.service.AuthService;
import com.leyou.common.auth.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/15/19 5:14 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description
 */
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 用户登录
     * <pre>createTime:
     * 7/15/19 5:18 PM</pre>
     *
     * @param username 用户名
     * @param password 密码
     * @param resp     response 对象
     */
    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletResponse resp
    ) {
        authService.login(username, password, resp);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 验证用户信息
     * <pre>createTime:
     * 7/15/19 9:05 PM</pre>
     *
     * @param req  请求
     * @param resp 响应
     * @return 用户信息
     */
    @GetMapping("verify")
    public ResponseEntity<UserInfo> verifyUser(HttpServletRequest req, HttpServletResponse resp) {
        return ResponseEntity.ok(authService.verifyUser(req, resp));
    }

    /**
     * 用户注销
     * <pre>createTime:
     * 7/15/19 9:21 PM</pre>
     *
     * @param req  请求
     * @param resp 响应
     */
    @PostMapping("logout")
    public ResponseEntity<Void> logout(HttpServletRequest req, HttpServletResponse resp) {
        authService.logout(req, resp);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 微服务认证并申请令牌
     * <pre>createTime:
     * 7/21/19 3:19 PM</pre>
     *
     * @param id     服务id
     * @param secret 密码
     * @return token
     */
    @GetMapping("/authorization")
    public ResponseEntity<String> authorize(@RequestParam("id") Long id, @RequestParam("secret") String secret) {
        return ResponseEntity.ok(authService.authenticate(id, secret));
    }
}
