package com.leyou.user.controller;

import com.leyou.user.entity.User;
import com.leyou.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/12/19 7:52 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 用户控制层
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 实现用户数据的校验，主要包括对：手机号、用户名的唯一性校验
     * <pre>createTime:
     * 7/12/19 8:05 PM</pre>
     *
     * @param data 要校验的数据
     * @param type 要校验的数据类型：1，用户名；2，手机；
     * @return true：可用；false：不可用；200：校验成功；400：参数有误；500：服务器内部异常
     */
    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<Boolean> check(
            @PathVariable String data,
            @PathVariable Integer type
    ) {
        return ResponseEntity.ok(userService.check(data, type));
    }

    /**
     * 发送短信验证码
     * <pre>createTime:
     * 7/13/19 3:05 PM</pre>
     *
     * @param phone 手机号
     * @return 204：请求已接收；400：参数有误；500：服务器内部异常
     */
    @PostMapping("/code")
    public ResponseEntity<Void> sendVerifyCode(@RequestParam("phone") String phone) {
        userService.sendVerifyCode(phone);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 用户注册
     * <pre>createTime:
     * 7/13/19 7:06 PM</pre>
     *
     * @param user username，password，phone
     * @param code 短信验证码
     * @return 201：注册成功；400：参数有误，注册失败；500：服务器内部异常，注册失败
     */
    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid User user, @RequestParam("code") String code) {
        userService.register(user, code);
        return ResponseEntity.ok().build();
    }
}
