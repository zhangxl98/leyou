package com.leyou.item.service;

import com.leyou.pojo.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 6/20/19 5:01 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 用于测试的 item 业务层
 */
@Service
@Slf4j
public class ItemService {

    public Item saveItem(Item item) {
        // 生成随机 id，代替插入数据库的操作
        int id = new Random().nextInt(100);
        item.setId(id);

        log.info("插入数据 -- Item：{}", item);
        return item;
    }
}
