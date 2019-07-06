package com.leyou.search.client;

import com.leyou.LySearchApplication;
import com.leyou.search.pojo.Goods;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/6/19 3:21 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 创建索引库
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LySearchApplication.class)
@Slf4j
public class CreateIndex {

    @Autowired
    private ElasticsearchTemplate esTemplate;
    
    /**
     * 创建索引库
     * 7/6/19 3:24 PM
     */
    @Test
    public void createIndex() throws Exception {
        esTemplate.createIndex(Goods.class);
        log.info("Create Index Success!");
    }

    /**
     * 创建映射
     * 7/6/19 3:28 PM
     */
    @Test
    public void putMapping() throws Exception {
        esTemplate.putMapping(Goods.class);
        log.info("Put Mapping Success!");
    }
}
