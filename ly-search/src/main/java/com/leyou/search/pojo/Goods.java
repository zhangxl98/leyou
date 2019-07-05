package com.leyou.search.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/4/19 9:21 AM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 要保存到索引库的商品实体类
 */
@Document(indexName = "goods", type = "docs", shards = 5, replicas = 1)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Goods {

    /**
     * SPU ID
     */
    @Id
    @Field(type = FieldType.Keyword)
    private Long id;

    /**
     * 标题
     */
    @Field(type = FieldType.Keyword, index = false)
    private String subTitle;

    /**
     * SKU 信息的 JSON 结构
     */
    @Field(type = FieldType.Keyword, index = false)
    private String skus;

    /**
     * 所有需要被搜索的信息，包含标题，分类，甚至品牌
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String all;

    /**
     * 品牌 id
     */
    private Long brandId;

    /**
     * 商品 3 级分类 id
     */
    private Long categoryId;

    /**
     * SPU 创建时间
     */
    private Long createTime;

    /**
     * 价格
     */
    private Set<Long> price;

    /**
     * 可搜索的规格参数，key 是参数名，value 是参数值
     */
    private Map<String, Object> specs;
}