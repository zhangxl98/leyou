package com.leyou.user.controller;

import com.leyou.user.dto.UserDTO;
import com.leyou.user.entity.User;
import com.leyou.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiOperation(value = "校验用户名数据是否可用，如果不存在则可用")
    @ApiResponses({
            @ApiResponse(code = 200, message = "校验结果有效，true 或 false 代表可用或不可用"),
            @ApiResponse(code = 400, message = "请求参数有误，比如 type 不是指定值")
    })
    public ResponseEntity<Boolean> check(
            @ApiParam(value = "要校验的数据", example = "jack") @PathVariable("data") String data,
            @ApiParam(value = "数据类型，1：用户名，2：手机号", example = "1") @PathVariable(value = "type") Integer type
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

    /**
     * 根据用户名和密码查询用户-用户登录
     * <pre>createTime:
     * 7/13/19 8:12 PM</pre>
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户数据
     */
    @GetMapping("/query")
    public ResponseEntity<UserDTO> queryByUsernameAndPassword(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        return ResponseEntity.ok(userService.queryByUsernameAndPassword(username, password));
    }
}
