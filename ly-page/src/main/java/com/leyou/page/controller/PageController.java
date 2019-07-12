package com.leyou.page.controller;

import com.leyou.page.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/11/19 9:09 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description
 */
@Controller
public class PageController {

    @Autowired
    private PageService pageService;

    /**
     * 返回对应的商品详情页
     * <pre>createTime:
     * 7/11/19 9:33 PM</pre>
     *
     * @param model
     * @param spuId
     * @return
     */
    @GetMapping("/item/{spuId}.html")
    public String toTtemPage(Model model, @PathVariable("spuId") Long spuId) {

        model.addAllAttributes(pageService.loadData(spuId));
        return "item";
    }
}
