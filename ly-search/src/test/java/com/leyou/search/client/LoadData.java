package com.leyou.search.client;

import com.leyou.LySearchApplication;
import com.leyou.common.vo.PageResult;
import com.leyou.dto.SpuDTO;
import com.leyou.item.client.ItemClient;
import com.leyou.search.repository.GoodsRepository;
import com.leyou.search.service.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/6/19 4:44 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 导入索引库数据
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LySearchApplication.class)
@Slf4j
public class LoadData {

    @Autowired
    private ItemClient itemClient;

    @Autowired
    private IndexService indexService;

    @Autowired
    private GoodsRepository goodsRepository;

    /**
     * 导入数据
     * 7/6/19 4:46 PM
     */
    @Test
    public void loadData() throws Exception {

        int page = 1;
        int rows = 100;

        while (true) {
            PageResult<SpuDTO> spuDTOPageResult = itemClient.querySpuListByPage(null, null, page, rows);

            if (null == spuDTOPageResult) {
                break;
            }

            page++;

            // 提取 SPU，并转换为 Goods 保存
            goodsRepository.saveAll(
                    spuDTOPageResult.getItems()
                            .stream()
                            .map(indexService::buildGoods)
                            .collect(Collectors.toList())
            );

            log.info("LoadData Success!");
        }
    }
}
