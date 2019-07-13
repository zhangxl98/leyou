package com.leyou.user.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.NumberUtils;
import com.leyou.common.utils.RegexUtils;
import com.leyou.user.entity.User;
import com.leyou.user.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.leyou.common.constants.MQConstants.Exchange.SMS_EXCHANGE_NAME;
import static com.leyou.common.constants.MQConstants.RoutingKey.VERIFY_CODE_KEY;

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

    /**
     * 验证码存入 redis 的前缀
     */
    public static final String KEY_PREFIX = "user:code:phone:";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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

    /**
     * 发送短信验证码
     * <pre>createTime:
     * 7/13/19 3:06 PM</pre>
     *
     * @param phone 手机号
     */
    public void sendVerifyCode(String phone) {

        try {
            // 对传入的手机号进行校验
            if (!RegexUtils.isPhone(phone)) {
                throw new LyException(ExceptionEnum.INVALID_PARAM_ERROR);
            }

            Map<String, String> msg = new HashMap<>(16);

            msg.put("phone", phone);

            // 生成验证码
            String code = NumberUtils.generateCode(6);

            msg.put("code", code);


            // 将验证码发送到消息中间件
            amqpTemplate.convertAndSend(SMS_EXCHANGE_NAME, VERIFY_CODE_KEY, msg);

            // 将验证码存入 redis 中，并设置时间为 5 分钟
            redisTemplate.opsForValue().set(KEY_PREFIX + phone, code, 5, TimeUnit.MINUTES);

        } catch (Exception e) {
            throw new LyException(ExceptionEnum.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 用户注册
     * <pre>createTime:
     * 7/13/19 7:07 PM</pre>
     *
     * @param user 用户名，格式为4~30位字母、数字、下划线；用户密码，格式为4~30位字母、数字、下划线；手机号码
     * @param code 短信验证码
     */
    public void register(User user, String code) {

        // 校验验证码
        String storeCode = redisTemplate.opsForValue().get(KEY_PREFIX + user.getPhone());

        if (!code.equals(storeCode)) {
            throw new LyException(ExceptionEnum.INVALID_PARAM_ERROR);
        }

        // 加密密码
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        // 保存用户
        if (1 != userMapper.insertSelective(user)) {
            throw new LyException(ExceptionEnum.INSERT_OPERATION_FAIL);
        }

        try {
            // 删除缓存中的验证码
            redisTemplate.delete(KEY_PREFIX + user.getPhone());
        } catch (Exception e) {
            throw new LyException(ExceptionEnum.DELETE_OPERATION_FAIL);
        }

    }
}
