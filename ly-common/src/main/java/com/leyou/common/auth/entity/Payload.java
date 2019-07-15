package com.leyou.common.auth.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/15/19 3:31 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 载荷对象
 */
@Data
public class Payload<T> {

    /**
     * tokenID
     */
    private String id;

    /**
     * 载荷的实际消息
     */
    private T info;

    /**
     * 过期时间
     */
    private Date expiration;
}
