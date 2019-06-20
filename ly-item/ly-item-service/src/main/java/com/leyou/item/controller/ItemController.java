package com.leyou.item.controller;

import com.leyou.item.service.ItemService;
import com.leyou.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 6/20/19 5:05 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 用于测试的 item 控制层
 */
@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping("item")
    public ResponseEntity<Item> saveItem(Item item) {
        // 如果价格为空，则抛出异常，返回 400 状态码，请求参数有误
        if (item.getPrice() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Item result = itemService.saveItem(item);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
