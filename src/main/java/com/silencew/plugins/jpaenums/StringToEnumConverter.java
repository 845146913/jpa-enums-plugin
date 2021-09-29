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
public class StringToEnumConverter<E extends Enum<E> & BaseEnum<?, String>> implements Converter<String, E> {
    private Map<String, E> enumMap = new HashMap<>();


    public <T extends E> StringToEnumConverter(Class<T> enumType) {
        E[] enums = enumType.getEnumConstants();
        for (E e : enums) {
            enumMap.put(String.valueOf(e instanceof BaseEnum ? e.getCode() : e.ordinal()), e);
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
