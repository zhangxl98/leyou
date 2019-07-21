package com.leyou.auth.config;

import com.leyou.common.auth.utils.RsaUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/15/19 4:47 PM
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
     * 私钥地址
     */
    private String priKeyPath;

    /**
     * 用户 token 相关属性
     */
    private UserTokenProperties user = new UserTokenProperties();

    /**
     * 服务 token 相关属性
     */
    private AppTokenProperties app = new AppTokenProperties();

    private PublicKey publicKey;
    private PrivateKey privateKey;

    @Data
    public class UserTokenProperties {

        /**
         * token 过期时长
         */
        private int expire;

        /**
         * 存放 token 的 cookie 名称
         */
        private String cookieName;

        /**
         * 存放 token 的 cookie 的 domain
         */
        private String cookieDomain;
    }

    @Data
    public class AppTokenProperties {

        /**
         * token 过期时长
         */
        private int expire;

        /**
         * 服务 id
         */
        private Long id;

        /**
         * 服务密码
         */
        private String secret;

        /**
         * 头信息
         */
        private String headerName;

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            // 获取公钥和私钥
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
            this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
        } catch (Exception e) {
            log.error("初始化公钥和私钥失败！", e);
            throw new RuntimeException(e);
        }
    }
}
