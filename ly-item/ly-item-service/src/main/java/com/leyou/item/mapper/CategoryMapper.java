package com.leyou.item.mapper;

import com.leyou.item.pojo.Category;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 6/23/19 4:27 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 分类通用 mapper 接口
 */
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 根据品牌 id 查询类目数据
     * <pre>createTime:
     * 7/1/19 4:21 PM</pre>
     *
     * @param brandId 品牌 id
     * @return 分类集合
     */
    List<Category> queryByBrandId(@Param("brandId") Long brandId);
}