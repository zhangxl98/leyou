package com.leyou.item.controller;

import com.leyou.common.vo.PageResult;
import com.leyou.item.service.BrandService;
import com.leyou.pojo.dto.BrandDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
