package com.leyou.sms.listener;

import com.leyou.common.exception.LyException;
import com.leyou.common.utils.JsonUtils;
import com.leyou.sms.config.SmsProperties;
import com.leyou.sms.utils.SmsHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.leyou.common.constants.MQConstants.Exchange.SMS_EXCHANGE_NAME;
import static com.leyou.common.constants.MQConstants.Queue.SMS_VERIFY_CODE_QUEUE;
import static com.leyou.common.constants.MQConstants.RoutingKey.VERIFY_CODE_KEY;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/13/19 4:57 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 短信消息监听器
 */
@Component
@EnableConfigurationProperties(SmsProperties.class)
@Slf4j
public class SmsListener {

    @Autowired
    private SmsProperties prop;

    @Autowired
    private SmsHelper smsHelper;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = SMS_VERIFY_CODE_QUEUE),
            exchange = @Exchange(name = SMS_EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            key = VERIFY_CODE_KEY
    ))
    public void listenVerifyCode(Map<String, String> msg) {
        if (msg == null) {
            return;
        }
        // 移除手机数据，剩下的是短信参数
        String phone = msg.remove("phone");
        if (StringUtils.isBlank(phone)) {
            return;
        }
        try {
            smsHelper.sendMessage(phone, prop.getSignName(), prop.getVerifyCodeTemplate(), JsonUtils.toString(msg));
        } catch (LyException e) {
            // 短信验证码失败后不重发，所以需要捕获异常。
            log.error("【SMS服务】短信验证码发送失败", e);
        }
    }
}
