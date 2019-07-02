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
import org.springframework.beans.factory.annotation.Autowired;
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
     * @return
     */
    public List<SpecGroupDTO> querySpecGroupByCategoryId(Long cid) {

        // 根据分类获取规格组
        List<SpecGroup> specGroupList = specGroupMapper.select(SpecGroup.builder().cid(cid).build());

        if (CollectionUtils.isEmpty(specGroupList)) {
            throw new LyException(ExceptionEnum.SPEC_NOT_FOND);
        }

        return BeanHelper.copyWithCollection(specGroupList, SpecGroupDTO.class);
    }

    /**
     * 返回规格参数信息
     * <pre>createTime:
     * 7/2/19 6:45 PM</pre>
     *
     * @param gid 规格组 Id
     * @return
     */
    public List<SpecParamDTO> querySpecParamBySpecGroupId(Long gid) {

        // 根据规格组获取规格参数
        List<SpecParam> specParamList = specParamMapper.select(SpecParam.builder().groupId(gid).build());

        if (CollectionUtils.isEmpty(specParamList)) {
            throw new LyException(ExceptionEnum.SPEC_NOT_FOND);
        }

        return BeanHelper.copyWithCollection(specParamList, SpecParamDTO.class);
    }
}
