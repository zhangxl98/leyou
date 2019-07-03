package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.BeanHelper;
import com.leyou.common.vo.PageResult;
import com.leyou.dto.CategoryDTO;
import com.leyou.dto.SpuDTO;
import com.leyou.item.mapper.SkuMapper;
import com.leyou.item.mapper.SpuDetailMapper;
import com.leyou.item.mapper.SpuMapper;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/2/19 9:37 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 商品业务层
 */
@Service
public class GoodsService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    /**
     * 返回分页数据
     * <pre>createTime:
     * 7/2/19 9:45 PM</pre>
     *
     * @param key      搜索关键字
     * @param saleable 是否上架
     * @param page     当前页，默认第 1 页
     * @param rows     每页大小，默认 5 条
     * @return 分页数据集合
     */
    public PageResult<SpuDTO> querySpuByPage(String key, Boolean saleable, Integer page, Integer rows) {

        PageHelper.startPage(page, rows);

        Example example = new Example(Spu.class);

        Example.Criteria criteria = example.createCriteria();

        // 搜索
        if (StringUtils.isNoneBlank(key)) {
            criteria.andLike("name", "%" + key + "%");
        }

        // 是否上架
        if (null != saleable) {
            criteria.andEqualTo("saleable", saleable);
        }

        // 默认按更新时间降序排序
        example.setOrderByClause("update_time DESC");

        List<Spu> spuList = spuMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(spuList)) {
            throw new LyException(ExceptionEnum.GOODS_NOT_FOND);
        }

        // 解析数据
        PageInfo<Spu> spuPageInfo = new PageInfo<>(spuList);

        // 转化为 DTO 对象
        List<SpuDTO> spuDTOList = BeanHelper.copyWithCollection(spuList, SpuDTO.class);

        spuDTOList.forEach(spuDTO -> {

            // 获取分类名称
            spuDTO.setCategoryName(categoryService.queryByIds(spuDTO.getCategoryIds()).stream().map(CategoryDTO::getName).collect(Collectors.joining("/")));

            // 获取品牌名称
            spuDTO.setBrandName(brandService.queryById(spuDTO.getBrandId()).getName());
        });

        return new PageResult<>(spuPageInfo.getTotal(), spuDTOList);
    }

    /**
     * 保存商品(SPU)
     * <pre>createTime:
     * 7/3/19 5:09 PM</pre>
     *
     * @param spuDTO SPU 对象
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveGoods(SpuDTO spuDTO) {

        // 获取 SPU 信息
        Spu spu = BeanHelper.copyProperties(spuDTO, Spu.class);

        // 保存 SPU 信息
        int count = spuMapper.insertSelective(spu);
        if (1 != count) {
            throw new LyException(ExceptionEnum.INSERT_OPERATION_FAIL);
        }

        // 获取 SPU 详情
        SpuDetail spuDetail = BeanHelper.copyProperties(spuDTO.getSpuDetail(), SpuDetail.class);

        spuDetail.setSpuId(spu.getId());

        // 保存 SPU 详情
        count = spuDetailMapper.insertSelective(spuDetail);
        if (1 != count) {
            throw new LyException(ExceptionEnum.INSERT_OPERATION_FAIL);
        }

        // 获取 SKU 集合
        BeanHelper.copyWithCollection(spuDTO.getSkus(), Sku.class).forEach(sku -> {
            sku.setSpuId(spu.getId());
            // 保存 SKU 信息
            if (1 != skuMapper.insertSelective(sku)) {
                throw new LyException(ExceptionEnum.INSERT_OPERATION_FAIL);

            }
        });
    }
}
