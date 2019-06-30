package com.leyou.upload.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 6/30/19 9:54 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 获取文件服务器地址
 */
@Data
@ConfigurationProperties(prefix = "file-server")
public class FileServerUrlProperties {

    /**
     * 文件服务器的 IP 和 PORT
     */
    private String url;
}
