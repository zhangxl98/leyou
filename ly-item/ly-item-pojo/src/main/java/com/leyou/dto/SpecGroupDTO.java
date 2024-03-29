package com.leyou.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/2/19 5:12 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 规格组转移对象
 */
@Data
public class SpecGroupDTO {

    /**
     * Id
     */
    private Long id;

    /**
     * 商品分类 Id
     */
    private Long cid;

    /**
     * 规格组名称
     */
    private String name;

    /**
     * 组内的参数，查询规格组同时查询组内参数
     */
    private List<SpecParamDTO> params;
}
