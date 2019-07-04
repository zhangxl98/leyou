package com.leyou.item.controller;

import com.leyou.dto.CategoryDTO;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 6/23/19 4:10 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 分类控制层
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 根据父节点查询商品类目
     * <pre>createTime:
     * 6/23/19 4:21 PM</pre>
     *
     * @param pid 父类目 id
     * @return 分类信息集合
     */
    @GetMapping("/of/parent")
    public ResponseEntity<List<CategoryDTO>> queryCategoryListByParentId(@RequestParam(value = "pid", defaultValue = "0") Long pid) {
        return ResponseEntity.ok(this.categoryService.queryCategoryListByParentId(pid));
    }

    /**
     * 根据品牌 id 返回类目信息
     * <pre>createTime:
     * 7/1/19 4:12 PM</pre>
     *
     * @param brandId 品牌 id
     * @return 分类集合
     */
    @GetMapping("/of/brand")
    public ResponseEntity<List<CategoryDTO>> queryCategoryListByBrandId(@RequestParam("id") Long brandId) {
        return ResponseEntity.ok(categoryService.queryCategoryListByBrandId(brandId));
    }

    /**
     * 根据商品分类查询商品分类信息
     * <pre>createTime:
     * 7/4/19 9:57 AM</pre>
     *
     * @param cids 商品分类 id 集合
     * @return 商品分类信息集合
     */
    @GetMapping("/list")
    public ResponseEntity<List<CategoryDTO>> queryCategoryListByIds(@RequestParam("ids") List<Long> cids) {
        return ResponseEntity.ok(categoryService.queryCategoryListByIds(cids));
    }
}