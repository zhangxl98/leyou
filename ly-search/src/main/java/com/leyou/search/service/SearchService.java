package com.leyou.search.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.BeanHelper;
import com.leyou.common.vo.PageResult;
import com.leyou.search.dto.GoodsDTO;
import com.leyou.search.dto.SearchRequest;
import com.leyou.search.pojo.Goods;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/6/19 10:02 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 搜索业务层
 */
@Service
public class SearchService {

    @Autowired
    private ElasticsearchTemplate esTemplate;

    /**
     * 搜索商品
     * <pre>createTime:
     * 7/5/19 4:20 PM</pre>
     *
     * @param request 搜索的参数，搜索关键字、页数
     * @return 商品列表
     */
    public PageResult<GoodsDTO> searchGoods(SearchRequest request) {

        // 判空
        if (StringUtils.isBlank(request.getKey())) {
            throw new LyException(ExceptionEnum.INVALID_PARAM_ERROR);
        }

        // 原生查询构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        // source 过滤
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id", "subTitle", "skus"}, null));

        // 搜索条件
        queryBuilder.withQuery(QueryBuilders.matchQuery("all", request.getKey()).operator(Operator.AND));

        // 分页
        queryBuilder.withPageable(PageRequest.of(request.getPage() - 1, request.getSize()));

        // 构建查询条件，得到查询结果
        AggregatedPage<Goods> result = esTemplate.queryForPage(queryBuilder.build(), Goods.class);

        // 解析查询结果
        long total = result.getTotalElements();
        int totalPages = result.getTotalPages();
        List<Goods> goodsList = result.getContent();

        // 返回
        return new PageResult<>(total, totalPages, BeanHelper.copyWithCollection(goodsList, GoodsDTO.class));
    }
}
