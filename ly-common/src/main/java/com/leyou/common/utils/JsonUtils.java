package com.leyou.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class JsonUtils {

    public static final ObjectMapper mapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    /**
     * 把一个对象序列化为String类型
     * <pre>createTime:
     * 6/20/19 4:03 PM</pre>
     *
     * @param obj 原始 java 对象
     * @return
     */
    public static String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj.getClass() == String.class) {
            return (String) obj;
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("json序列化出错：" + obj, e);
            return null;
        }
    }

    /**
     * 把一个 json 反序列化为 java 对象
     *
     * @param json   要反序列化的 json 字符串
     * @param tClass 返回的 java 类型
     * @param <T>
     * @return
     */
    public static <T> T toBean(String json, Class<T> tClass) {
        try {
            return mapper.readValue(json, tClass);
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }

    /**
     * 把一个 json 反序列化为 List 类型
     * <pre>createTime:
     * 6/20/19 4:03 PM</pre>
     *
     * @param json   要反序列化的 json 字符串
     * @param eClass 集合中元素类型
     * @param <E>
     * @return
     */
    public static <E> List<E> toList(String json, Class<E> eClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, eClass));
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }

    /**
     * 把一个 json 反序列化为 Map 类型
     * <pre>createTime:
     * 6/20/19 4:04 PM</pre>
     *
     * @param json   要反序列化的 json 字符串
     * @param kClass 集合中 key 的类型
     * @param vClass 集合中 value 的类型
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> toMap(String json, Class<K> kClass, Class<V> vClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructMapType(Map.class, kClass, vClass));
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }

    /**
     * 把 json 字符串反序列化，当反序列化的结果比较复杂时，通过这个方法转换
     * <pre>在传参时，需要传递 TypeReference 的匿名内部类，</pre>
     * <pre>把要返回的类型写在 TypeReference 的泛型中，</pre>
     * <pre>则返回的就是泛型中类型。</pre>
     * <pre>例如：
     * List<User> users = JsonUtils.nativeRead(json, new TypeReference<List<User>>() {});</pre>
     * <pre>createTime:
     * 6/20/19 4:06 PM</pre>
     *
     * @param json 要反序列化的 json 字符串
     * @param type 返回的类型
     * @param <T>
     * @return
     */
    public static <T> T nativeRead(String json, TypeReference<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }
}
