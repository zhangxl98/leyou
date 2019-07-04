package com.leyou.search.client;

import com.leyou.LySearchApplication;
import com.leyou.item.client.ItemClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/4/19 2:59 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 测试 FeignClient 功能
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LySearchApplication.class)
@Slf4j
public class CategoryClientTest {

    @Autowired
    private ItemClient itemClient;

    /**
     * 查找分类
     * 7/4/19 3:01 PM
     */
    @Test
    public void testQueryByIdList() throws Exception {
        itemClient.queryCategoryListByIds(Arrays.asList(1L, 2L, 3L)).forEach(categoryDTO -> log.info("categoryDTO = {}", categoryDTO));
    }
}
