package com.leyou.item.controller;

import com.leyou.common.vo.PageResult;
import com.leyou.dto.SpuDTO;
import com.leyou.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping
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
    public ResponseEntity<PageResult<SpuDTO>> querySpuByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows
    ) {
        return ResponseEntity.ok(goodsService.querySpuByPage(key, saleable, page, rows));
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
}
