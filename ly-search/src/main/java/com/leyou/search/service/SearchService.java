package com.leyou.search.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.BeanHelper;
import com.leyou.common.vo.PageResult;
import com.leyou.dto.BrandDTO;
import com.leyou.dto.CategoryDTO;
import com.leyou.item.client.ItemClient;
import com.leyou.search.dto.GoodsDTO;
import com.leyou.search.dto.SearchRequest;
import com.leyou.search.pojo.Goods;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilterBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private ItemClient itemClient;

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

    /**
     * 返回过滤项
     * <pre>createTime:
     * 7/7/19 4:03 PM</pre>
     *
     * @param request 搜索的参数，搜索关键字、页数
     * @return 搜索过滤项
     */
    public Map<String, List<?>> queryFilters(SearchRequest request) {

        // 分类聚合名
        String categoryAgg = "categoryAgg";
        // 品牌聚合名
        String brandAgg = "brandAgg";

        // 创建搜索过滤项集合
        LinkedHashMap<String, List<?>> filterList = new LinkedHashMap<>();

        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders.matchQuery("all", request.getKey()).operator(Operator.AND));
        // 每页显示 1 个
        queryBuilder.withPageable(PageRequest.of(0, 1));
        // 显示空的 source
        queryBuilder.withSourceFilter(new FetchSourceFilterBuilder().build());

        // 构建聚合条件
        // 分类聚合
        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAgg).field("categoryId"));
        queryBuilder.addAggregation(AggregationBuilders.terms(brandAgg).field("brandId"));

        // 得到查询结果
        AggregatedPage<Goods> result = esTemplate.queryForPage(queryBuilder.build(), Goods.class);

        // 解析查询结果
        Aggregations aggregations = result.getAggregations();

        // 获取分类聚合
        LongTerms categoryTerms = aggregations.get(categoryAgg);
        // 解析 bucket，获取 id 集合
        List<Long> categoryIds = categoryTerms.getBuckets()
                .stream()
                .map(LongTerms.Bucket::getKeyAsNumber)
                .map(Number::longValue)
                .collect(Collectors.toList());
        // 根据 id 集合获取分类
        List<CategoryDTO> categoryDTOList = itemClient.queryCategoryListByIds(categoryIds);
        filterList.put("分类", categoryDTOList);

        // 获取品牌聚合
        LongTerms brandTerms = aggregations.get(brandAgg);
        // 解析 bucket，获取 id 集合
        List<Long> brandIds = brandTerms.getBuckets()
                .stream()
                .map(LongTerms.Bucket::getKeyAsNumber)
                .map(Number::longValue)
                .collect(Collectors.toList());
        // 根据 id 集合获取分类
        List<BrandDTO> brandDTOList = itemClient.queryBrandByIds(brandIds);
        filterList.put("品牌", brandDTOList);

        return filterList;
    }
}
