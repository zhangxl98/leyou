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
 * @Date 7/2/19 8:25 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description SPU 实体类
 */
@Table(name = "tb_spu")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Spu {

    /**
     * SPU ID
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 副标题，一般是促销信息
     */
    private String subTitle;

    /**
     * 1 级类目 Id
     */
    private Long cid1;

    /**
     * 2 级类目 Id
     */
    private Long cid2;

    /**
     * 3 级类目 Id
     */
    private Long cid3;

    /**
     * 商品所属品牌 Id
     */
    private Long brandId;

    /**
     * 是否上架
     */
    private Boolean saleable;

    /**
     * 数据创建时间
     */
    private Date createTime;

    /**
     * 数据更新时间
     */
    private Date updateTime;
}
