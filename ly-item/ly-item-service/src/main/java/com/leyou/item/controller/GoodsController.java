package com.leyou.item.controller;

import com.leyou.common.vo.PageResult;
import com.leyou.dto.SkuDTO;
import com.leyou.dto.SpuDTO;
import com.leyou.dto.SpuDetailDTO;
import com.leyou.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/2/19 9:35 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 商品控制层
 */
@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 返回分页数据
     * <pre>createTime:
     * 7/2/19 9:42 PM</pre>
     *
     * @param key      搜索关键字
     * @param saleable 是否上架
     * @param page     当前页，默认第 1 页
     * @param rows     每页大小，默认 5 条
     * @return 分页数据集合
     */
    @GetMapping("/spu/page")
    public ResponseEntity<PageResult<SpuDTO>> querySpuListByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows
    ) {
        return ResponseEntity.ok(goodsService.querySpuListByPage(key, saleable, page, rows));
    }

    /**
     * 新增商品(SPU)
     * <pre>createTime:
     * 7/3/19 5:04 PM</pre>
     *
     * @param spuDTO SPU 对象
     * @return 201
     */
    @PostMapping("/goods")
    public ResponseEntity<Void> saveGoods(@RequestBody SpuDTO spuDTO) {
        goodsService.saveGoods(spuDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 商品下架
     * <pre>createTime:
     * 7/3/19 6:55 PM</pre>
     *
     * @param id       商品(SPU) id
     * @param saleable 是否下架
     * @return 204
     */
    @PutMapping("/spu/saleable")
    public ResponseEntity<Void> updateSpuSaleable(
            @RequestParam("id") Long id,
            @RequestParam("saleable") Boolean saleable
    ) {
        goodsService.updateSpuSaleable(id, saleable);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 返回商品详细信息，数据回显
     * <pre>createTime:
     * 7/3/19 7:18 PM</pre>
     *
     * @param spuId 商品(SPU) id
     * @return 商品详细信息
     */
    @GetMapping("/spu/detail")
    public ResponseEntity<SpuDetailDTO> querySpuDetailBySpuId(@RequestParam("id") Long spuId) {
        return ResponseEntity.ok(goodsService.querySpuDetailBySpuId(spuId));
    }

    /**
     * 返回商品信息
     * <pre>createTime:
     * 7/11/19 9:58 PM</pre>
     *
     * @param spuId 商品(SPU) id
     * @return 商品信息
     */
    @GetMapping("/spu/{id}")
    public ResponseEntity<SpuDTO> querySpuBySpuId(@PathVariable("id") Long spuId) {
        return ResponseEntity.ok(goodsService.querySpuBySpuId(spuId));
    }

    /**
     * 返回商品 SKU 信息，数据回显
     * <pre>createTime:
     * 7/3/19 7:50 PM</pre>
     *
     * @param spuId 商品(SPU) id
     * @return SKU 信息集合
     */
    @GetMapping("/sku/of/spu")
    public ResponseEntity<List<SkuDTO>> querySkuListBySpuId(@RequestParam("id") Long spuId) {
        return ResponseEntity.ok(goodsService.querySkuListBySpuId(spuId));
    }

    /**
     * 修改商品(SPU)
     * <pre>createTime:
     * 7/3/19 8:58 PM</pre>
     *
     * @param spuDTO SPU 对象
     * @return 204
     */
    @PutMapping("/goods")
    public ResponseEntity<Void> updateGoods(@RequestBody SpuDTO spuDTO) {
        goodsService.updateGoods(spuDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除商品
     * <pre>createTime:
     * 7/3/19 9:40 PM</pre>
     *
     * @param spuId 要删除的商品 id
     * @return 204
     */
    @DeleteMapping("/goods/{id}")
    public ResponseEntity<Void> deleteGoods(@PathVariable("id") Long spuId) {
        goodsService.deleteGoods(spuId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 批量查询商品 sku
     * <pre>createTime:
     * 7/23/19 9:20 AM</pre>
     *
     * @param ids 多个 sku id
     * @return sku 集合
     */
    @GetMapping("sku/list")
    public ResponseEntity<List<SkuDTO>> querySkuListByIds(@RequestParam("ids") List<Long> ids) {

        return ResponseEntity.ok(this.goodsService.querySkuListByIds(ids));
    }
}
