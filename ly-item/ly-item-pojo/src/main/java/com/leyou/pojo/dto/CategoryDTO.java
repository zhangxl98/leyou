package com.leyou.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 6/23/19 4:08 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 分类转移对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {
    /**
     * 类目 id
     */
    private Long id;

    /**
     * 类目名称
     */
    private String name;

    /**
     * 父类目 id,顶级类目填 0
     */
    private Long parentId;

    /**
     * 是否为父节点，0 为否，1 为是
     */
    private Boolean isParent;

    /**
     * 排序指数，越小越靠前
     */
    private Integer sort;
}
