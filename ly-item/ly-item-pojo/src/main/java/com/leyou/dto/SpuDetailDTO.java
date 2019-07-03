package com.leyou.dto;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/3/19 4:56 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description SPU 详情转移对象
 */
@Data
public class SpuDetailDTO {

    /**
     * 对应的 SPU 的 Id
     */
    private Long spuId;

    /**
     * 商品 描述信息
     */
    private String description;

    /**
     * 通用规格属性
     */
    private String genericSpec;

    /**
     * 商品特殊规格的名称及可选值模板，JSON 格式
     */
    private String specialSpec;

    /**
     * 包装清单
     */
    private String packingList;

    /**
     * 售后服务
     */
    private String afterService;
}
