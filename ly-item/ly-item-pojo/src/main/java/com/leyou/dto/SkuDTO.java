package com.leyou.dto;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/3/19 4:55 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description SKU 转移对象
 */
@Data
public class SkuDTO {

    /**
     * SKU ID
     */
    private Long id;

    /**
     * SPU ID
     */
    private Long spuId;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 商品的图片，多个图片以‘,’分割
     */
    private String images;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 销售价格,单位为 分
     */
    private Long price;

    /**
     * 特有规格属性在 SPU 属性模板中的对应下标组合
     */
    private String indexes;

    /**
     * SKU 的特有规格参数键值对，JSON 格式，反序列化时请使用 linkedHashMap，保证有序
     */
    private String ownSpec;

    /**
     * 是否有效，逻辑删除用
     */
    private Boolean enable;
}
