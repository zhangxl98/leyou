package com.leyou.user.service;

import com.leyou.user.mapper.UserMapper;
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
}
