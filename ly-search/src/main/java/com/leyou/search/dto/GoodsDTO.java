package com.leyou.search.dto;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/5/19 4:07 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 商品传输对象
 */
@Data
public class GoodsDTO {

    /**
     * SPU id
     */
    private Long id;

    /**
     * 副标题，卖点
     */
    private String subTitle;

    /**
     * SKU 的 JSON 数据
     */
    private String skus;
}
