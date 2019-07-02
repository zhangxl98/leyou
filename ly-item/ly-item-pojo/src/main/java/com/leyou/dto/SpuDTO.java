package com.leyou.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/2/19 8:56 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description SPU 转移对象
 */
@Data
public class SpuDTO {

    /**
     * SPU ID
     */
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
     * 商品分类名称拼接
     */
    private String categoryName;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 方便同时获取3级分类
     * @return
     */
    @JsonIgnore
    public List<Long> getCategoryIds(){
        return Arrays.asList(cid1, cid2, cid3);
    }
}
