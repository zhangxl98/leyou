package com.leyou.user.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.user.entity.User;
import com.leyou.user.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/12/19 7:51 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 用户业务层
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 实现用户数据的校验，主要包括对：手机号、用户名的唯一性校验
     * <pre>createTime:
     * 7/12/19 8:20 PM</pre>
     *
     * @param data 要校验的数据
     * @param type 要校验的数据类型：1，用户名；2，手机；
     * @return true：可用；false：不可用
     */
    public Boolean check(String data, Integer type) {

        // 没有 data 参数，返回 400
        if (StringUtils.isBlank(data)) {
            throw new LyException(ExceptionEnum.INVALID_PARAM_ERROR);
        }

        User record = new User();
        switch (type) {
            case 1:
                record.setUsername(data);
                break;
            case 2:
                record.setPhone(data);
                break;
            default:
                // type 不为 1 或 2，返回 400
                throw new LyException(ExceptionEnum.INVALID_PARAM_ERROR);
        }

        int count;
        try {
            count = userMapper.selectCount(record);
        } catch (Exception e) {
            throw new LyException(ExceptionEnum.INTERNAL_SERVER_ERROR);
        }
        return 1 != count;
    }
}
