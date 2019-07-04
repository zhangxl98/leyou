package com.leyou.search.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.leyou.common.utils.JsonUtils;
import com.leyou.dto.*;
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
 * @Date 7/4/19 3:33 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 搜索业务层
 */
@Service
public class SearchService {

    @Autowired
    private ItemClient itemClient;

    /**
     * 将一个 SPU 转化为一个 Goods 对象
     * <pre>createTime:
     * 7/4/19 3:41 PM</pre>
     *
     * @param spuDTO SPU对象
     * @return Goods
     */
    public Goods buildGoods(SpuDTO spuDTO) {

        // 获取商品相关搜索信息的拼接：名称、分类、品牌、规格信息
        // 分类
        String categoryNames = itemClient.queryCategoryListByIds(spuDTO.getCategoryIds())
                .stream().map(CategoryDTO::getName).collect(Collectors.joining(","));
        // 品牌
        BrandDTO brand = itemClient.queryBrandById(spuDTO.getBrandId());
        // 名称等，完成拼接
        String all = spuDTO.getName() + categoryNames + brand.getName();

        // 获取 SPU 下的所有 SKU 的 JSON 数组，并存储到 Map 中
        ArrayList<Map<String, Object>> skuMapList = new ArrayList<>();
        Map<String, Object> skuMap = new HashMap<>(16);
        // 获取 SPU 下的所有 SKU 价格的集合
        Set<Long> priceSet = new HashSet<>();
        itemClient.querySkuListBySpuId(spuDTO.getId()).forEach(skuDTO -> {
            skuMap.put("id", skuDTO.getId());
            skuMap.put("price", skuDTO.getPrice());
            skuMap.put("title", skuDTO.getTitle());
            skuMap.put("image", StringUtils.substringBefore(skuDTO.getImages(), ","));
            skuMapList.add(skuMap);
            priceSet.add(skuDTO.getPrice());
        });

        // 获取 SPU 的规格参数
        Map<String, Object> specMap = new HashMap<>(16);

        // 获取规格参数的 key
        List<SpecParamDTO> specParamDTOS = itemClient.querySpecParamsListByGroupIdOrCategoryId(null, spuDTO.getCid3(), true);

        // 获取规格参数的值
        SpuDetailDTO spuDetailDTO = itemClient.querySpuDetailBySpuId(spuDTO.getId());

        // 获取通用规格参数值
        Map<Long, Object> genericSpec = JsonUtils.toMap(spuDetailDTO.getGenericSpec(), Long.class, Object.class);

        // 获取特有规格参数值
        Map<Long, List<String>> specialSpecMap = JsonUtils.nativeRead(spuDetailDTO.getSpecialSpec(), new TypeReference<Map<Long, List<String>>>() {
        });

        specParamDTOS.forEach(specParamDTO -> {
            // 获取规格参数的名称
            String key = specParamDTO.getName();

            // 获取规格参数值
            Object value = null;
            // 判断是否是通用规格
            if (specParamDTO.getGeneric()) {
                // 通用规格
                value = genericSpec.get(specParamDTO.getId());
            } else {
                // 特有规格
                value = specialSpecMap.get(specParamDTO.getId());
            }

            // 判断是否是数字类型
            if (specParamDTO.getNumeric()) {
                // 是数字类型，进行分段
                value = chooseSegment(value, specParamDTO);
            }
        });

        return Goods.builder()
                .id(spuDTO.getId())
                .subTitle(spuDTO.getSubTitle())
                // SPU 下的所有 SKU 的 JSON 数组
                .skus(JsonUtils.toString(skuMap))
                .all(all)
                .brandId(spuDTO.getBrandId())
                .categoryId(spuDTO.getCid3())
                .createTime(spuDTO.getCreateTime().getTime())
                // 当前 SPU 下所有 SKU 的价格的集合
                .price(priceSet)
                // 当前 SPU 的规格参数
                .specs(specMap)
                .build();
    }

    /**
     * 处理数值类型的规格
     * <pre>createTime:
     * 7/4/19 8:34 PM</pre>
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
     * 7/4/19 8:35 PM</pre>
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
