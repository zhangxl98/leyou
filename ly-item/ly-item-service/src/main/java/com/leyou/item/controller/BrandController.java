package com.leyou.item.controller;

import com.leyou.common.vo.PageResult;
import com.leyou.dto.BrandDTO;
import com.leyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 6/28/19 8:05 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 品牌控制层
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 返回分页数据
     * <pre>createTime:
     * 6/28/19 8:12 PM</pre>
     *
     * @param page   当前页，默认第 1 页
     * @param rows   每页大小，默认 5 条
     * @param key    搜索关键字
     * @param sortBy 排序字段
     * @param desc   是否为降序
     * @return
     */
    @GetMapping("/page")
    public ResponseEntity<PageResult<BrandDTO>> queryBrandByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc
    ) {
        return ResponseEntity.ok(brandService.queryBrandByPage(page, rows, key, sortBy, desc));
    }

    /**
     * 新增品牌
     * <pre>createTime:
     * 6/30/19 5:06 PM</pre>
     *
     * @param brandDTO 品牌对象
     * @param cids     商品分类 id 数组
     * @return 状态码
     */
    @PostMapping
    public ResponseEntity<Void> saveBrand(BrandDTO brandDTO, @RequestParam("cids") List<Long> cids) {

        brandService.saveBrand(brandDTO, cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改品牌
     * <pre>createTime:
     * 7/1/19 4:42 PM</pre>
     *
     * @param brandDTO 品牌对象
     * @param cids     商品分类 id 数组
     * @return 状态码
     */
    @PutMapping
    public ResponseEntity<Void> updateBrand(BrandDTO brandDTO, @RequestParam("cids") List<Long> cids) {
        brandService.updateBrand(brandDTO, cids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除品牌
     * <pre>createTime:
     * 7/1/19 6:54 PM</pre>
     *
     * @param bid 品牌 id
     * @return 状态码
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteBrand(@RequestParam("bid") Long bid) {
        brandService.deleteBrand(bid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}