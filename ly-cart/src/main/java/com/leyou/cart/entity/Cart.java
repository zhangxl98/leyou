package com.leyou.cart.entity;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/23/19 10:06 AM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 购物车
 */
@Data
public class Cart {

    /**
     * 商品 id
     */
    private Long skuId;

    /**
     * 标题
     */
    private String title;

    /**
     * 图片
     */
    private String image;

    /**
     * 加入购物车时的价格
     */
    private Long price;

    /**
     * 购买数量
     */
    private Integer num;

    /**
     * 商品规格参数
     */
    private String ownSpec;
}
