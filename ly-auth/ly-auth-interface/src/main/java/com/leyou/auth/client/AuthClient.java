package com.leyou.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/21/19 5:21 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 授权中心 Feign 接口
 */
@FeignClient("auth-service")
public interface AuthClient {

    /**
     * 微服务认证并申请令牌
     * <pre>createTime:
     * 7/21/19 5:23 PM</pre>
     *
     * @param id     服务id
     * @param secret 密码
     * @return token
     */
    @GetMapping("authorization")
    String authorize(@RequestParam("id") Long id, @RequestParam("secret") String secret);
}
