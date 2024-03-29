package com.leyou.item.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.BeanHelper;
import com.leyou.dto.CategoryDTO;
import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 6/23/19 4:15 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 分类业务层
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据父节点，返回商品类目
     *
     * @param pid 父类目 id
     * @return
     */
    public List<CategoryDTO> queryCategoryListByParentId(Long pid) {

        // mapper 将对象中的非空属性作为查询条件
        List<Category> categoryList = categoryMapper.select(Category.builder().parentId(pid).build());

        // 判断结果
        if (CollectionUtils.isEmpty(categoryList)) {
            // 未查到数据，返回 204
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOND);
        }

        // 使用自定义工具类，把 Category 集合转换为 DTO 的集合
        return BeanHelper.copyWithCollection(categoryList, CategoryDTO.class);
    }

    /**
     * 根据品牌 id 返回类目数据
     * <pre>createTime:
     * 7/1/19 4:21 PM</pre>
     *
     * @param brandId 品牌 id
     * @return 分类集合
     */
    public List<CategoryDTO> queryCategoryListByBrandId(Long brandId) {

        List<Category> categoryList = categoryMapper.queryCategoryListByBrandId(brandId);

        if (CollectionUtils.isEmpty(categoryList)) {
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOND);
        }
        return BeanHelper.copyWithCollection(categoryList, CategoryDTO.class);

    }

    /**
     * 返回类目数据
     * <pre>createTime:
     * 7/2/19 9:26 PM</pre>
     *
     * @param ids 类目 id 列表
     * @return 类目数据
     */
    public List<CategoryDTO> queryCategoryListByIds(List<Long> ids) {

        // 查询
        List<Category> categoryList = categoryMapper.selectByIdList(ids);

        if (CollectionUtils.isEmpty(categoryList)) {
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOND);
        }

        return BeanHelper.copyWithCollection(categoryList, CategoryDTO.class);
    }
}