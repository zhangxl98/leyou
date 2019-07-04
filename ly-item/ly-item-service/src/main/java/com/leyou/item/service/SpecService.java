package com.leyou.item.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.BeanHelper;
import com.leyou.dto.SpecGroupDTO;
import com.leyou.dto.SpecParamDTO;
import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/2/19 5:15 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 商品规格业务层
 */
@Service
public class SpecService {

    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

    /**
     * 返回规格组信息
     * <pre>createTime:
     * 7/2/19 5:21 PM</pre>
     *
     * @param cid 分类 Id
     * @return 规格组集合
     */
    public List<SpecGroupDTO> querySpecGroupListByCategoryId(Long cid) {

        // 根据分类获取规格组
        List<SpecGroup> specGroupList = specGroupMapper.select(SpecGroup.builder().cid(cid).build());

        if (CollectionUtils.isEmpty(specGroupList)) {
            throw new LyException(ExceptionEnum.SPEC_NOT_FOND);
        }

        return BeanHelper.copyWithCollection(specGroupList, SpecGroupDTO.class);
    }

    /**
     * 保存规格组
     * <pre>createTime:
     * 7/2/19 7:16 PM</pre>
     *
     * @param specGroupDTO 规格组信息
     */
    public void saveSpecGroup(SpecGroupDTO specGroupDTO) {

        // 保存
        if (1 != specGroupMapper.insertSelective(BeanHelper.copyProperties(specGroupDTO, SpecGroup.class))) {
            throw new LyException(ExceptionEnum.INSERT_OPERATION_FAIL);
        }
    }

    /**
     * 修改规格组
     * <pre>createTime:
     * 7/2/19 7:26 PM</pre>
     *
     * @param specGroupDTO 规格组信息
     */
    public void updateSpecGroup(SpecGroupDTO specGroupDTO) {

        // 修改
        if (1 != specGroupMapper.updateByPrimaryKeySelective(BeanHelper.copyProperties(specGroupDTO, SpecGroup.class))) {
            throw new LyException(ExceptionEnum.INSERT_OPERATION_FAIL);
        }
    }

    /**
     * 删除规格组
     * <pre>createTime:
     * 7/2/19 7:33 PM</pre>
     *
     * @param gid 规格组 Id
     */
    public void deleteSpecGroup(Long gid) {

        // 删除
        if (1 != specGroupMapper.deleteByPrimaryKey(gid)) {
            throw new LyException(ExceptionEnum.DELETE_OPERATION_FAIL);
        }
    }

    /**
     * 返回规格参数信息
     * <pre>createTime:
     * 7/3/19 4:06 PM</pre>
     *
     * @param gid       规格组 id
     * @param cid       分类 id
     * @param searching 是否用于搜索
     * @return 规格参数集合
     */
    public List<SpecParamDTO> querySpecParamsListByGroupIdOrCategoryId(Long gid, Long cid, Boolean searching) {

        if (null == gid && null == cid) {
            throw new LyException(ExceptionEnum.INVALID_PARAM_ERROR);
        }

        // 根据分类 或 规格组获取规格参数
        List<SpecParam> specParamList = specParamMapper.select(SpecParam.builder().cid(cid).groupId(gid).searching(searching).build());

        if (CollectionUtils.isEmpty(specParamList)) {
            throw new LyException(ExceptionEnum.SPEC_NOT_FOND);
        }

        return BeanHelper.copyWithCollection(specParamList, SpecParamDTO.class);
    }

    /**
     * 新增规格参数
     * <pre>createTime:
     * 7/2/19 7:52 PM</pre>
     *
     * @param specParamDTO 规格参数信息
     */
    public void saveSpecParam(SpecParamDTO specParamDTO) {

        // 保存
        if (1 != specParamMapper.insertSelective(BeanHelper.copyProperties(specParamDTO, SpecParam.class))) {
            throw new LyException(ExceptionEnum.INSERT_OPERATION_FAIL);
        }
    }

    /**
     * 修改规格参数
     * <pre>createTime:
     * 7/2/19 7:53 PM</pre>
     *
     * @param specParamDTO 规格参数信息
     */
    public void updateSpecParam(SpecParamDTO specParamDTO) {

        // 修改
        if (1 != specParamMapper.updateByPrimaryKeySelective(BeanHelper.copyProperties(specParamDTO, SpecParam.class))) {
            throw new LyException(ExceptionEnum.UPDATE_OPERATION_FAIL);
        }
    }

    /**
     * 删除规格参数
     * <pre>createTime:
     * 7/2/19 7:56 PM</pre>
     *
     * @param pid 规格组 Id
     */
    public void deleteSpecParam(Long pid) {

        // 删除
        if (1 != specParamMapper.deleteByPrimaryKey(pid)) {
            throw new LyException(ExceptionEnum.DELETE_OPERATION_FAIL);
        }
    }
}
