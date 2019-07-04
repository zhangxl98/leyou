package com.leyou.item.client;

import com.leyou.common.vo.PageResult;
import com.leyou.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/4/19 12:25 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 对外提供的商品 Feign 接口
 */
@FeignClient("item-service")
public interface ItemClient {

    /**
     * 根据品牌 id 返回品牌数据
     * <pre>createTime:
     * 7/4/19 12:33 PM</pre>
     *
     * @param bid 品牌 id
     * @return 品牌数据信息
     */
    @GetMapping("/brand/{id}")
    BrandDTO queryBrandById(@PathVariable("id") Long bid);

    /**
     * 根据商品分类查询商品分类信息
     * <pre>createTime:
     * 7/4/19 12:34 PM</pre>
     *
     * @param cids 商品分类 id 集合
     * @return 商品分类信息集合
     */
    @GetMapping("/category/list")
    List<CategoryDTO> queryCategoryListByIds(@RequestParam("ids") List<Long> cids);

    /**
     * 返回分页数据
     * <pre>createTime:
     * 7/4/19 12:36 PM</pre>
     *
     * @param key      搜索关键字
     * @param saleable 是否上架
     * @param page     当前页，默认第 1 页
     * @param rows     每页大小，默认 5 条
     * @return 分页数据集合
     */
    @GetMapping("/spu/page")
    PageResult<SpuDTO> querySpuListByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows
    );

    /**
     * 返回商品详细信息，数据回显
     * <pre>createTime:
     * 7/4/19 12:36 PM</pre>
     *
     * @param spuId 商品(SPU) id
     * @return 商品详细信息
     */
    @GetMapping("/spu/detail")
    SpuDetailDTO querySpuDetailBySpuId(@RequestParam("id") Long spuId);

    /**
     * 返回商品 SKU 信息，数据回显
     * <pre>createTime:
     * 7/4/19 12:38 PM</pre>
     *
     * @param spuId 商品(SPU) id
     * @return SKU 信息集合
     */
    @GetMapping("/sku/of/spu")
    List<SkuDTO> querySkuListBySpuId(@RequestParam("id") Long spuId);

    /**
     * 返回规格参数信息
     * <pre>createTime:
     * 7/4/19 7:00 PM</pre>
     *
     * @param gid       规格组 id
     * @param cid       分类 id
     * @param searching 是否用于搜索
     * @return 规格参数集合
     */
    @GetMapping("/spec/params")
    List<SpecParamDTO> querySpecParamsListByGroupIdOrCategoryId(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "searching", required = false) Boolean searching);
}
