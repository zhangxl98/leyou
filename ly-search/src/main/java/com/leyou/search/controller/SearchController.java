package com.leyou.search.controller;

import com.leyou.common.vo.PageResult;
import com.leyou.search.dto.GoodsDTO;
import com.leyou.search.dto.SearchRequest;
import com.leyou.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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

    /**
     * 返回过滤项
     * <pre>createTime:
     * 7/7/19 4:00 PM</pre>
     *
     * @param searchRequest 搜索的参数，搜索关键字、页数
     * @return 搜索过滤项
     */
    @PostMapping("/filter")
    public ResponseEntity<Map<String, List<?>>> queryFilters(@RequestBody SearchRequest searchRequest) {
        return ResponseEntity.ok(searchService.queryFilters(searchRequest));
    }
}
