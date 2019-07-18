package com.leyou.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/18/19 8:10 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 白名单配置
 */
@Data
@ConfigurationProperties(prefix = "ly.filter")
public class FilterProperties {

    private Set<String> allowPaths;
}
