package com.leyou.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/13/19 4:48 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 阿里短信属性类
 */
@Data
@ConfigurationProperties(prefix = "ly.sms")
public class SmsProperties {

    /**
     * 账号
     */
    String accessKeyID;

    /**
     * 密钥
     */
    String accessKeySecret;

    /**
     * 短信签名
     */
    String signName;

    /**
     * 短信模板
     */
    String verifyCodeTemplate;

    /**
     * 发送短信请求的域名
     */
    String domain;

    /**
     * API版本
     */
    String version;

    /**
     * API类型
     */
    String action;

    /**
     * 区域
     */
    String regionID;
}
