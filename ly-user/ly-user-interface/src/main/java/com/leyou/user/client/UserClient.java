package com.leyou.user.client;

import com.leyou.user.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/15/19 5:03 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 用户中心 Feign 接口
 */
@FeignClient("user-service")
public interface UserClient {

    /**
     * 根据用户名和密码查询用户-用户登录
     * <pre>createTime:
     * 7/15/19 5:17 PM</pre>
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户数据
     */
    @GetMapping("/query")
    UserDTO queryByUsernameAndPassword(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    );
}
