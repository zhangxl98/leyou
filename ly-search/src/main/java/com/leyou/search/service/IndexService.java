package com.leyou.search.service;

import com.leyou.common.utils.JsonUtils;
import com.leyou.dto.CategoryDTO;
import com.leyou.dto.SpuDTO;
import com.leyou.item.client.ItemClient;
import com.leyou.search.pojo.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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

        // skus 将 SPU 下所有的 SKU 的 JSON 数据存储到 Map 中
        Map<String, Object> skuMap = new HashMap<>(16);
        // 最终要加入 Goods 的 List
        List<Map<String, Object>> skuMapList = new ArrayList<>();
        // price Set 去重
        Set<Long> priceSet = new HashSet<>();
        itemClient.querySkuListBySpuId(spuDTO.getId()).forEach(skuDTO -> {
            skuMap.put("id", skuDTO.getId());
            skuMap.put("price", skuDTO.getPrice());
            skuMap.put("title", skuDTO.getTitle());
            skuMap.put("image", skuDTO.getImages().split(","));

            skuMapList.add(skuMap);

            priceSet.add(skuDTO.getPrice());
        });
        String skus = JsonUtils.toString(skuMapList);


        return Goods.builder()
                .id(id)
                .brandId(brandId)
                .subTitle(subTitle)
                .categoryId(categoryId)
                .createTime(createTime)
                .all(all)
                .skus(skus)
                .price(priceSet)
                .build();
    }
}
