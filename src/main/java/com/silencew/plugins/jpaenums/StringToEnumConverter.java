package com.silencew.plugins.jpaenums;

import org.springframework.core.convert.converter.Converter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 通用请求参数枚举转换
 * Created by IntelliJ IDEA.
 * author: wangshuiping
 * date: 2020/12/30
 */
public class StringToEnumConverter<E extends BaseEnum<?, String>> implements Converter<String, E> {
    private Map<String, E> enumMap = new HashMap<>();

    public StringToEnumConverter(Class<E> enumType) {
        E[] enums = enumType.getEnumConstants();
        for (E e : enums) {
            enumMap.put(String.valueOf(e.getCode()), e);
        }
    }

    @Override
    public E convert(String code) {
        E e = enumMap.get(code);
        if (Objects.isNull(e)) {
            throw new IllegalArgumentException("无法匹配对应的枚举类型");
        }
        return e;
    }
}
