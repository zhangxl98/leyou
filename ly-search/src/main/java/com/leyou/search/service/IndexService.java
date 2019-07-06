package com.leyou.search.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.leyou.common.utils.JsonUtils;
import com.leyou.dto.CategoryDTO;
import com.leyou.dto.SpecParamDTO;
import com.leyou.dto.SpuDTO;
import com.leyou.dto.SpuDetailDTO;
import com.leyou.item.client.ItemClient;
import com.leyou.search.pojo.Goods;
import org.apache.commons.lang3.StringUtils;
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

        // specs
        // 最终加入 Goods 的规格参数集合
        Map<String, Object> specMap = new HashMap<>();
        // 根据分类获取可搜索的规格参数 ==> 规格参数的 key
        List<SpecParamDTO> specParamDTOS = itemClient.querySpecParamsListByGroupIdOrCategoryId(null, spuDTO.getCid3(), true);
        // 获取规格参数的 value
        SpuDetailDTO spuDetailDTO = itemClient.querySpuDetailBySpuId(spuDTO.getId());

        // 通用参数规格，并将 JSON 字符串转换为 Map
        Map<Long, Object> genericSpecMap = JsonUtils.nativeRead(spuDetailDTO.getGenericSpec(), new TypeReference<Map<Long, Object>>() {
        });
        // 特有参数规格，并将 JSON 字符串转换为 Map
        Map<Long, List<String>> specialSpecMap = JsonUtils.nativeRead(spuDetailDTO.getSpecialSpec(), new TypeReference<Map<Long, List<String>>>() {
        });

        // 遍历 key
        specParamDTOS.forEach(specParamDTO -> {
            // 获取规格参数的名称
            String key = specParamDTO.getName();

            // 获取规格参数的值
            Object value;
            // 判断是否为通用规格
            if (specParamDTO.getGeneric()) {
                // 通用规格
                value = genericSpecMap.get(specParamDTO.getId());
            } else {
                // 特有规格
                value = specialSpecMap.get(specParamDTO.getId());
            }

            // 判断是否为数值型规格
            if (specParamDTO.getNumeric()) {
                // 分段
                value = chooseSegment(value, specParamDTO);
            }
            // 将数据添加到 specMap
            specMap.put(key, value);
        });


        return Goods.builder()
                .id(id)
                .brandId(brandId)
                .subTitle(subTitle)
                .categoryId(categoryId)
                .createTime(createTime)
                .all(all)
                .skus(skus)
                .price(priceSet)
                .specs(specMap)
                .build();
    }

    /**
     * 处理数值类型的规格
     * <pre>createTime:
     * 7/6/19 9:46 PM</pre>
     *
     * @param value 数值型规格参数值
     * @param p     规格参数
     * @return 分好段的数值字符串
     */
    private String chooseSegment(Object value, SpecParamDTO p) {

        if (value == null || StringUtils.isBlank(value.toString())) {
            return "其它";
        }
        double val = parseDouble(value.toString());
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = parseDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if (segs.length == 2) {
                end = parseDouble(segs[1]);
            }
            // 判断是否在范围内
            if (val >= begin && val < end) {
                if (segs.length == 1) {
                    result = segs[0] + p.getUnit() + "以上";
                } else if (begin == 0) {
                    result = segs[1] + p.getUnit() + "以下";
                } else {
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }

    /**
     * String 转 double
     * <pre>createTime:
     * 7/6/19 9:46 PM</pre>
     *
     * @param str 要转化的字符串
     * @return 浮点数结果
     */
    private double parseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            return 0;
        }
    }
}
