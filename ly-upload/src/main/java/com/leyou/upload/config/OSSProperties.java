package com.leyou.upload.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/1/19 10:41 AM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description OSS配置类
 */
@Data
@ConfigurationProperties("ly.oss")
@Component
public class OSSProperties {

    /**
     * AccessKeyId
     */
    private String accessKeyId;

    /**
     * AccessKeySecret
     */
    private String accessKeySecret;

    /**
     * bucketName
     */
    private String bucket;

    /**
     * host 的格式为 bucketName.endpoint
     */
    private String host;

    /**
     * endpoint
     */
    private String endpoint;

    /**
     * 用户上传文件时指定的前缀
     */
    private String dir;

    /**
     * 过期时间
     */
    private long expireTime;

    /**
     * 文件大小限制
     */
    private long maxFileSize;
}
