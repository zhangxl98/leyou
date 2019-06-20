package com.leyou.common.enums;

import lombok.Getter;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 6/20/19 7:08 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 封装异常状态码和异常信息的枚举类
 */
@Getter
public enum ExceptionEnum {

    PRICE_CANNOT_BE_NULL(400, "价格不能为空！");

    private int status;

    private String maeesge;

    ExceptionEnum(int status, String maeesge) {
        this.status = status;
        this.maeesge = maeesge;
    }
}
