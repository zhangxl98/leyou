package com.leyou.item.controller;

import com.leyou.dto.SpecGroupDTO;
import com.leyou.dto.SpecParamDTO;
import com.leyou.item.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/2/19 5:08 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 商品规格控制层
 */
@RestController
@RequestMapping("/spec")
public class SpecController {

    @Autowired
    private SpecService specService;

    /**
     * 返回规格组信息
     * <pre>createTime:
     * 7/2/19 5:21 PM</pre>
     *
     * @param cid 分类 Id
     * @return
     */
    @RequestMapping("groups/of/category")
    public ResponseEntity<List<SpecGroupDTO>> querySpecGroupByCategoryId(@RequestParam("id") Long cid) {
        return ResponseEntity.ok(specService.querySpecGroupByCategoryId(cid));
    }

    /**
     * 返回规格参数信息
     * <pre>createTime:
     * 7/2/19 6:43 PM</pre>
     *
     * @param gid 规格组 Id
     * @return
     */
    @RequestMapping("/params")
    public ResponseEntity<List<SpecParamDTO>> querySpecParamBySpecGroupId(@RequestParam("gid") Long gid) {
        return ResponseEntity.ok(specService.querySpecParamBySpecGroupId(gid));
    }
}
