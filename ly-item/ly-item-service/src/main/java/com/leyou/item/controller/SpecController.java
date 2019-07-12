package com.leyou.item.controller;

import com.leyou.dto.SpecGroupDTO;
import com.leyou.dto.SpecParamDTO;
import com.leyou.item.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * @return 规格组集合
     */
    @GetMapping("/groups/of/category")
    public ResponseEntity<List<SpecGroupDTO>> querySpecGroupListByCategoryId(@RequestParam("id") Long cid) {
        return ResponseEntity.ok(specService.querySpecGroupListByCategoryId(cid));
    }

    /**
     * 查询规格参数组，及组内参数
     * <pre>createTime:
     * 7/11/19 10:15 PM</pre>
     *
     * @param cid 商品分类 id
     * @return 规格组及组内参数
     */
    @GetMapping("/of/category")
    public ResponseEntity<List<SpecGroupDTO>> querySpecListByCategoryId(@RequestParam("id") Long cid) {
        return ResponseEntity.ok(specService.querySpecListByCategoryId(cid));
    }

    /**
     * 新增规格组
     * <pre>createTime:
     * 7/2/19 7:15 PM</pre>
     *
     * @param specGroupDTO 规格组信息
     * @return 201
     */
    @PostMapping("/group")
    public ResponseEntity<Void> saveSpecGroup(@RequestBody SpecGroupDTO specGroupDTO) {
        specService.saveSpecGroup(specGroupDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改规格组
     * <pre>createTime:
     * 7/2/19 7:25 PM</pre>
     *
     * @param specGroupDTO 规格组信息
     * @return 204
     */
    @PutMapping("/group")
    public ResponseEntity<Void> updateSpecGroup(@RequestBody SpecGroupDTO specGroupDTO) {
        specService.updateSpecGroup(specGroupDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除规格组
     * <pre>createTime:
     * 7/2/19 7:29 PM</pre>
     *
     * @param gid 规格组 Id
     * @return 204
     */
    @DeleteMapping("/group/{gid}")
    public ResponseEntity<Void> deleteSpecGroup(@PathVariable("gid") Long gid) {
        specService.deleteSpecGroup(gid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 返回规格参数信息
     * <pre>createTime:
     * 7/3/19 4:05 PM</pre>
     *
     * @param gid       规格组 id
     * @param cid       分类 id
     * @param searching 是否用于搜索
     * @return 规格参数集合
     */
    @GetMapping("/params")
    public ResponseEntity<List<SpecParamDTO>> querySpecParamsListByGroupIdOrCategoryId(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "searching", required = false) Boolean searching) {
        return ResponseEntity.ok(specService.querySpecParamsListByGroupIdOrCategoryId(gid, cid, searching));
    }

    /**
     * 新增规格参数
     * <pre>createTime:
     * 7/2/19 7:41 PM</pre>
     *
     * @param specParamDTO 规格参数信息
     * @return 201
     */
    @PostMapping("/param")
    public ResponseEntity<Void> saveSpecParam(@RequestBody SpecParamDTO specParamDTO) {
        specService.saveSpecParam(specParamDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改规格参数
     * <pre>createTime:
     * 7/2/19 7:52 PM</pre>
     *
     * @param specParamDTO 规格参数信息
     * @return 204
     */
    @PutMapping("/param")
    public ResponseEntity<Void> updateSpecParam(@RequestBody SpecParamDTO specParamDTO) {
        specService.updateSpecParam(specParamDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除规格参数
     * <pre>createTime:
     * 7/2/19 7:56 PM</pre>
     *
     * @param pid 规格组 Id
     * @return 204
     */
    @DeleteMapping("/param/{pid}")
    public ResponseEntity<Void> deleteSpecParam(@PathVariable("pid") Long pid) {
        specService.deleteSpecParam(pid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
