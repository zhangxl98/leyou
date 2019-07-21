package com.leyou.gateway.config;

import com.leyou.common.auth.utils.RsaUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.PublicKey;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/18/19 7:10 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description JWT 配置
 */
@ConfigurationProperties(prefix = "ly.jwt")
@Data
@Slf4j
public class JwtProperties implements InitializingBean {

    /**
     * 公钥地址
     */
    private String pubKeyPath;

    /**
     * 用户 token 相关属性
     */
    private UserTokenProperties user = new UserTokenProperties();

    /**
     * 服务 token 相关属性
     */
    private AppTokenInfo app = new AppTokenInfo();

    private PublicKey publicKey;

    @Data
    public class UserTokenProperties {

        /**
         * 存放 token 的 cookie 名称
         */
        private String cookieName;
    }

    @Data
    public class AppTokenInfo{

        private Long id;
        private String secret;
        private String headerName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            // 获取公钥
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        } catch (Exception e) {
            log.error("初始化公钥失败！", e);
            throw new RuntimeException(e);
        }
    }
}
