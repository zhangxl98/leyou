package com.leyou.item.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.BeanHelper;
import com.leyou.dto.SpecGroupDTO;
import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.pojo.SpecGroup;
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

    /**
     * 返回规格组信息
     * <pre>createTime:
     * 7/2/19 5:21 PM</pre>
     *
     * @param cid 分类 Id
     * @return
     */
    public List<SpecGroupDTO> queryByCategoryId(Long cid) {

        // 根据分类获取规格组
        List<SpecGroup> groupList = specGroupMapper.select(SpecGroup.builder().cid(cid).build());

        if (CollectionUtils.isEmpty(groupList)) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }

        return BeanHelper.copyWithCollection(groupList, SpecGroupDTO.class);
    }
}
