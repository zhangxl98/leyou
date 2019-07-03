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
    public ResponseEntity<List<SpecGroupDTO>> querySpecGroupByCategoryId(@RequestParam("id") Long cid) {
        return ResponseEntity.ok(specService.querySpecGroupByCategoryId(cid));
    }

    /**
     * 新增规格组
     * <pre>createTime:
     * 7/2/19 7:15 PM</pre>
     *
     * @param specGroupDTO 规格组信息
     * @return 状态码
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
     * @return 状态码
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
     * @return 状态码
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
     * @param gid 规格组 id
     * @param cid 分类 id
     * @return 规格参数集合
     */
    @GetMapping("/params")
    public ResponseEntity<List<SpecParamDTO>> querySpecParams(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid) {
        return ResponseEntity.ok(specService.querySpecParams(gid, cid));
    }

    /**
     * 新增规格参数
     * <pre>createTime:
     * 7/2/19 7:41 PM</pre>
     *
     * @param specParamDTO 规格参数信息
     * @return 状态码
     */
    @PostMapping("/param")
    public ResponseEntity<List<SpecParamDTO>> saveSpecParamBySpecGroupId(@RequestBody SpecParamDTO specParamDTO) {
        specService.saveSpecParamBySpecGroupId(specParamDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改规格参数
     * <pre>createTime:
     * 7/2/19 7:52 PM</pre>
     *
     * @param specParamDTO 规格参数信息
     * @return 状态码
     */
    @PutMapping("/param")
    public ResponseEntity<List<SpecParamDTO>> updateSpecParamBySpecGroupId(@RequestBody SpecParamDTO specParamDTO) {
        specService.updateSpecParamBySpecGroupId(specParamDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 删除规格组
     * <pre>createTime:
     * 7/2/19 7:56 PM</pre>
     *
     * @param pid 规格组 Id
     * @return 状态码
     */
    @DeleteMapping("/param/{pid}")
    public ResponseEntity<List<SpecParamDTO>> deleteSpecParamBySpecGroupId(@PathVariable("pid") Long pid) {
        specService.deleteSpecParamBySpecGroupId(pid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
