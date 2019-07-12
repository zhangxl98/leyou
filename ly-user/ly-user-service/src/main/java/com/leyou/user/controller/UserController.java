package com.leyou.user.controller;

import com.leyou.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}
