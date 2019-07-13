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
    CATEGORY_NOT_FOND(204, "分类信息未找到"),
    SPEC_NOT_FOND(204, "规格参数未找到"),
    DATA_TRANSFER_ERROR(500, "数据转换异常"),
    BRAND_NOT_FOUND(204, "品牌不存在"),
    FILE_UPLOAD_ERROR(500, "文件上传失败"),
    INSERT_OPERATION_FAIL(500, "数据新增失败"),
    UPDATE_OPERATION_FAIL(500, "数据修改失败"),
    DELETE_OPERATION_FAIL(500, "数据删除失败"),
    INVALID_FILE_TYPE(500, "文件类型错误"),
    GOODS_NOT_FOND(204, "商品未找到"),
    INVALID_PARAM_ERROR(400, "请求参数有误"),
    SPU_DETAIL_NOT_FOND(204, "商品详情未找到"),
    SKU_NOT_FOND(204, "商品 SKU 未找到"),
    SPU_NOT_FOND(204, "商品 SPU 未找到"),
    DIRECTORY_WRITER_ERROR(500, "目录未找到"),
    FILE_WRITER_ERROR(500, "文件写入失败"),
    INTERNAL_SERVER_ERROR(500, "服务器内部异常"),
    SEND_MESSAGE_ERROR(500, "短信发送失败");

    private int status;

    private String message;

    ExceptionEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }
}