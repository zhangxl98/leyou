package com.leyou.item.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/2/19 8:44 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description SPU 详情实体类
 */
@Table(name = "tb_spu_detail")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpuDetail {

    /**
     * 对应的 SPU 的 Id
     */
    @Id
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

    /**
     * 数据创建时间
     */
    private Date createTime;

    /**
     * 数据更新时间
     */
    private Date updateTime;
}
