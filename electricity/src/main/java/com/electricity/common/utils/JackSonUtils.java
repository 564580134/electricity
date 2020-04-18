package com.electricity.common.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * @Description: Json工具类
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/

public class JackSonUtils {

    /**
     * Json字符串转对象
     *
     * @param <T>     泛型
     * @param jsonStr json字符串
     * @param clazz   转换成的对象
     * @return T
     * @throws Exception 异常
     */
    public static <T> T jsonStrToBean(String jsonStr, Class<T> clazz) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonStr, clazz);
    }

    /**
     * 对象转Json字符串
     *
     * @param bean 对象
     * @return String
     * @throws Exception 异常
     */
    public static String beanToJsonStr(Object bean) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(bean);
    }

    /**
     * 将json数据转换成pojo对象list
     *
     * @return list
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        ObjectMapper mapper = new ObjectMapper();
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            List<T> list = mapper.readValue(jsonData, javaType);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
