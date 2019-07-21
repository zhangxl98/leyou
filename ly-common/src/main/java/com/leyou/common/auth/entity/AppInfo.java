package com.leyou.common.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/21/19 3:17 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 载荷：服务信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppInfo {

    /**
     * 服务 id
     */
    private Long id;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 目标服务列表
     */
    private List<Long> targetList;
}