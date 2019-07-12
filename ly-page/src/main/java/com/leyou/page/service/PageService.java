package com.leyou.page.service;

import com.leyou.dto.*;
import com.leyou.item.client.ItemClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/11/19 9:37 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description
 */
@Service
public class PageService {

    @Autowired
    private ItemClient itemClient;

    /**
     * 返回商品详情页数据
     * <pre>createTime:
     * 7/11/19 9:39 PM</pre>
     *
     * @param spuId 商品 id
     * @return 商品详情页数据
     */
    public Map<String, Object> loadData(Long spuId) {

        Map<String, Object> result = new HashMap<>(16);

        List<SkuDTO> skuDTOS = itemClient.querySkuListBySpuId(spuId);

        SpuDTO spuDTO = itemClient.querySpuBySpuId(spuId);

        SpuDetailDTO spuDetailDTO = itemClient.querySpuDetailBySpuId(spuId);

        List<CategoryDTO> categoryDTOS = itemClient.queryCategoryListByIds(spuDTO.getCategoryIds());

        List<SpecGroupDTO> specGroupDTOS = itemClient.querySpecListByCategoryId(spuDTO.getCid3());

        BrandDTO brandDTO = itemClient.queryBrandById(spuDTO.getBrandId());

        result.put("skus", skuDTOS);
        result.put("spuName", spuDTO.getName());
        result.put("subTitle", spuDTO.getSubTitle());
        result.put("detail", spuDetailDTO);
        result.put("specs", specGroupDTOS);
        result.put("categories", categoryDTOS);
        result.put("brand", brandDTO);

        return result;
    }
}
