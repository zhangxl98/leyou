package com.leyou.auth.mapper;

import com.leyou.auth.entity.ApplicationInfo;
import com.leyou.common.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/21/19 3:11 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description
 */
public interface ApplicationInfoMapper extends BaseMapper<ApplicationInfo> {

    /**
     * 根据服务 id 查询目标服务 id 列表
     * <pre>createTime:
     * 7/21/19 3:15 PM</pre>
     *
     * @param serviceId 服务 id
     * @return 目标服务 id 列表
     */
    List<Long> queryTargetIdList(@Param("serviceId") Long serviceId);
}
