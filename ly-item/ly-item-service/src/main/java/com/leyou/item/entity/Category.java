package com.leyou.item.entity;

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
 * @Date 6/23/19 3:53 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 分类实体类
 */
@Table(name="tb_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    /**
     * 类目 id
     */
    @Id
    @KeySql(useGeneratedKeys = true)
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

    /**
     * 数据创建时间
     */
    private Date createTime;

    /**
     * 数据更新时间
     */
    private Date updateTime;
}
