package com.leyou.search.client;

import com.leyou.LySearchApplication;
import com.leyou.common.vo.PageResult;
import com.leyou.dto.SpuDTO;
import com.leyou.item.client.ItemClient;
import com.leyou.search.repository.GoodsRepository;
import com.leyou.search.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/4/19 9:04 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 导入索引数据
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LySearchApplication.class)
public class LoadDataTest {

    @Autowired
    private SearchService searchService;

    @Autowired
    private ElasticsearchTemplate esTemplate;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ItemClient itemClient;

    /**
     * 导入数据
     * 7/4/19 9:06 PM
     */
    @Test
    public void loadData() throws Exception {

        int page = 1;
        int rows = 100;
        int size = 0;

        do {
            try {
                // 获取分页数据
                PageResult<SpuDTO> pageResult = itemClient.querySpuListByPage(null, true, page, rows);

                // 提取 SPU，并转换为 Goods 保存
                goodsRepository.saveAll(pageResult.getItems().stream().map(searchService::buildGoods).collect(Collectors.toList()));

                page++;
                size = pageResult.getItems().size();
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        } while (size == 100);

    }
}
