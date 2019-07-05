package com.leyou.search.controller;

import com.leyou.common.vo.PageResult;
import com.leyou.search.dto.GoodsDTO;
import com.leyou.search.dto.SearchRequest;
import com.leyou.search.pojo.Goods;
import com.leyou.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/5/19 4:10 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 搜索控制层
 */
@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * 搜索商品
     * <pre>createTime:
     * 7/5/19 4:19 PM</pre>
     *
     * @param searchRequest 搜索的参数，搜索关键字、页数
     * @return 商品列表
     */
    @PostMapping("/page")
    public ResponseEntity<PageResult<GoodsDTO>> searchGoods(@RequestBody SearchRequest searchRequest) {
        return ResponseEntity.ok(searchService.searchGoods(searchRequest));
    }
}
