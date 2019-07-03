package com.leyou.item.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/3/19 4:49 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description SKU 实体类
 */
@Table(name = "tb_sku")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sku {

    /**
     * SKU ID
     */
    @Id
    @KeySql(useGeneratedKeys=true)
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

    /**
     * 数据创建时间
     */
    private Date createTime;

    /**
     * 数据更新时间
     */
    private Date updateTime;
}
