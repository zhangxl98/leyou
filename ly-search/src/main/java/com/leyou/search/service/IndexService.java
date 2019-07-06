package com.leyou.search.service;

import com.leyou.common.utils.BeanHelper;
import com.leyou.dto.CategoryDTO;
import com.leyou.dto.SpuDTO;
import com.leyou.item.client.ItemClient;
import com.leyou.search.pojo.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/6/19 3:11 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 导入数据业务
 */
@Service
public class IndexService {

    @Autowired
    private ItemClient itemClient;

    /**
     * 把一个 SPU 转为一个 Goods 对象
     * <pre>createTime:
     * 7/6/19 5:31 PM</pre>
     *
     * @param spuDTO 要转换的 Spu 对象
     * @return 转换好的 Goods 对象
     */
    public Goods buildGoods(SpuDTO spuDTO) {

        // id
        Long id = spuDTO.getId();

        // brandId
        Long brandId = spuDTO.getBrandId();

        // subTitle
        String subTitle = spuDTO.getSubTitle();

        // categoryId
        Long categoryId = spuDTO.getCid3();

        // createTime
        long createTime = spuDTO.getCreateTime().getTime();

        // all 所有需要被搜索的信息，包含 商品名称、分类、品牌
        // 根据分类的 ID 集合查询分类的集合，然后循环得到 name 并拼接，已空格分隔
        String categoryName = itemClient.queryCategoryListByIds(spuDTO.getCategoryIds())
                .stream()
                .map(CategoryDTO::getName)
                .collect(Collectors.joining(" "));
        // 根据品牌 ID 获取品牌 name
        String brandName = itemClient.queryBrandById(spuDTO.getBrandId()).getName();
        String all = spuDTO.getName() + categoryName + brandName;


        return Goods.builder()
                .id(id)
                .brandId(brandId)
                .subTitle(subTitle)
                .categoryId(categoryId)
                .createTime(createTime)
                .all(all)
                .build();
    }
}
