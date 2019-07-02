package com.leyou.dto;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/2/19 6:41 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 规格参数转移对象
 */
@Data
public class SpecParamDTO {
    /**
     * Id
     */
    private Long id;

    /**
     * 商品分类 Id
     */
    private Long cid;

    /**
     * 规格组 Id
     */
    private Long groupId;

    /**
     * 参数名
     */
    private String name;

    /**
     * 是否是数字类型参数，true/false
     */
    private Boolean numeric;

    /**
     * 数字类型参数的单位，非数字类型可以为空
     */
    private String unit;

    /**
     * 是否是 sku 通用属性，true/false
     */
    private Boolean generic;

    /**
     * 是否用于搜索过滤，true/false
     */
    private Boolean searching;

    /**
     * 数值类型参数，如果需要搜索，则添加分段间隔值，如 CPU 频率间隔：0.5-1.0
     */
    private String segments;
}
