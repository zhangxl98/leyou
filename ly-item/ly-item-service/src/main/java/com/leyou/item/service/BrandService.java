package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.BeanHelper;
import com.leyou.common.vo.PageResult;
import com.leyou.dto.BrandDTO;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 6/28/19 8:17 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 品牌业务层
 */
@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 返回分页数据
     * <pre>createTime:
     * 6/28/19 8:18 PM</pre>
     *
     * @param page   当前页，默认第 1 页
     * @param rows   每页大小，默认 5 条
     * @param key    搜索关键字
     * @param sortBy 排序字段
     * @param desc   是否为降序
     * @return
     */
    public PageResult<BrandDTO> queryBrandByPage(Integer page, Integer rows, String key, String sortBy, Boolean desc) {

        PageHelper.startPage(page, rows);

        Example example = new Example(Brand.class);

        if (StringUtils.isNoneBlank(key)) {
            example.createCriteria().orLike("name", "%" + key + "%").orEqualTo("letter", key.toUpperCase());
        }

        if (StringUtils.isNoneBlank(sortBy)) {
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }


        // 根据条件查询
        List<Brand> brands = brandMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(brands)) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }

        // 解析数据
        PageInfo<Brand> brandPageInfo = new PageInfo<>(brands);

        // 将 Brand 集合转化为 BrandDTO 集合
        List<BrandDTO> list = BeanHelper.copyWithCollection(brands, BrandDTO.class);

        return new PageResult<>(brandPageInfo.getTotal(), list);
    }

    /**
     * 新增品牌
     * <pre>createTime:
     * 6/30/19 5:07 PM</pre>
     *
     * @param brandDTO brand 对象
     * @param cids     商品分类 id 数组
     */
    @Transactional
    public void saveBrand(BrandDTO brandDTO, List<Long> cids) {

        // 将 BrandDTO 转化为 Brand 对象
        Brand brand = BeanHelper.copyProperties(brandDTO, Brand.class);

        // 保存品牌
        int count = brandMapper.insertSelective(brand);
        if (count!=1){
            throw  new LyException(ExceptionEnum.INSERT_OPERATION_FAIL);
        }

        // 维护中间表
        count = brandMapper.insertCategoryBrand(brand.getId(), cids);
        if (count!=cids.size()){
            throw  new LyException(ExceptionEnum.INSERT_OPERATION_FAIL);
        }
    }
}
