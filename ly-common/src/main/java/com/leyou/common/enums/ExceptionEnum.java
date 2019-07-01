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

    PRICE_CANNOT_BE_NULL(400, "价格不能为空"),
    CATEGORY_NOT_FOND(204, "父类目找不到"),
    DATA_TRANSFER_ERROR(500, "数据转换异常"),
    BRAND_NOT_FOUND(204, "品牌不存在"),
    INSERT_OPERATION_FAIL(500, "新增失败"),
    FILE_UPLOAD_ERROR(500, "文件上传失败"),
    UPDATE_OPERATION_FAIL(500, "数据修改失败");

    private int status;

    private String message;

    ExceptionEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }
}