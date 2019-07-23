package com.leyou.cart.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.leyou.cart.config.JwtProperties;
import com.leyou.cart.entity.Cart;
import com.leyou.common.auth.entity.Payload;
import com.leyou.common.auth.entity.UserInfo;
import com.leyou.common.auth.utils.JwtUtils;
import com.leyou.common.utils.CookieUtils;
import com.leyou.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/23/19 10:10 AM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description
 */
@Service
@EnableConfigurationProperties(JwtProperties.class)
public class CartService {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String KEY_PREFIX = "ly:cart:user:id:";


    /**
     * 加入购物车
     * <pre>createTime:
     * 7/23/19 10:26 AM</pre>
     *
     * @param cart
     */
    public void addCart(Cart cart, HttpServletRequest req) {

        String token = CookieUtils.getCookieValue(req, jwtProperties.getUser().getCookieName());

        Payload<UserInfo> payload = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey(), UserInfo.class);

        UserInfo userInfo = payload.getInfo();

        String key = KEY_PREFIX + (userInfo.getId().longValue());

        BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(key);

        if (ops.hasKey(cart.getSkuId().toString())) {
            //存在，改数量
            String cartJson = ops.get(cart.getSkuId().toString());
            Cart storeCart = JsonUtils.nativeRead(cartJson, new TypeReference<Cart>() {
            });
            //改数量
            storeCart.setNum(storeCart.getNum() + cart.getNum());

            ops.put(storeCart.getSkuId().toString(), JsonUtils.toString(storeCart));

        } else {
            //不存在，直接加
            //key：value全是string
            ops.put(cart.getSkuId().toString(), JsonUtils.toString(cart));
        }
    }
}
