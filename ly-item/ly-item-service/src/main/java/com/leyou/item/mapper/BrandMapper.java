package com.leyou.item.mapper;

import com.leyou.common.mapper.BaseMapper;
import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 6/28/19 7:46 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 品牌通用 mapper 接口
 */
public interface BrandMapper extends BaseMapper<Brand> {

    /**
     * 维护中间表
     * <pre>createTime:
     * 6/30/19 6:48 PM</pre>
     *
     * @param bid  品牌 id
     * @param cids 商品分类 id 数组
     * @return 数据库变更条数
     */
    int insertCategoryBrand(@Param("bid") Long bid, @Param("cids") List<Long> cids);


    /**
     * 删除中间表
     * <pre>createTime:
     * 7/1/19 4:57 PM</pre>
     *
     * @param bid 品牌 id
     * @return 数据库变更条数
     */
    int deleteCategoryBrandByBrandId(@Param("bid") Long bid);

    /**
     * 根据分类 id 查询品牌列表
     * <pre>createTime:
     * 7/3/19 3:15 PM</pre>
     *
     * @param cid 分类 id
     * @return 品牌集合
     */
    List<Brand> queryBrandListByCategoryId(@Param("cid") Long cid);
}