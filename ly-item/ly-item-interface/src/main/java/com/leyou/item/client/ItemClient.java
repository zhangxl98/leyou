package com.leyou.item.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/4/19 12:25 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 对外提供的商品 Feign 接口
 */
@FeignClient("item-service")
public interface ItemClient {


}
