package com.leyou.item.client;

import com.leyou.common.vo.PageResult;
import com.leyou.dto.BrandDTO;
import com.leyou.dto.CategoryDTO;
import com.leyou.dto.SpuDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
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
     * 返回分页数据
     * <pre>createTime:
     * 7/6/19 4:53 PM</pre>
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
     * 根据商品分类查询商品分类信息
     * <pre>createTime:
     * 7/6/19 6:52 PM</pre>
     *
     * @param cids 商品分类 id 集合
     * @return 商品分类信息集合
     */
    @GetMapping("/category/list")
    List<CategoryDTO> queryCategoryListByIds(@RequestParam("ids") List<Long> cids);

    /**
     * 根据品牌 id 返回品牌数据
     * <pre>createTime:
     * 7/6/19 7:00 PM</pre>
     *
     * @param bid 品牌 id
     * @return 品牌数据信息
     */
    @GetMapping("/brand/{id}")
    BrandDTO queryBrandById(@PathVariable("id") Long bid);
}
