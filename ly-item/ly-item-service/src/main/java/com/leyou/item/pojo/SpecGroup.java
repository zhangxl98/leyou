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
 * @Date 7/2/19 5:00 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 规格组实体类
 */
@Table(name = "tb_spec_group")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecGroup {

    @Id
    @KeySql(useGeneratedKeys = true)
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
     * 数据创建时间
     */
    private Date createTime;

    /**
     * 数据更新时间
     */
    private Date updateTime;
}
