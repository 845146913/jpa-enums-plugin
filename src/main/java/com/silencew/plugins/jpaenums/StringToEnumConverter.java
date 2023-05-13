package com.silencew.plugins.jpaenums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Map<String, E> enumMap = new HashMap<>();


    public <T extends E> StringToEnumConverter(Class<T> enumType) {
        E[] enums = enumType.getEnumConstants();
        for (E e : enums) {
            if (e == null) {
                continue;
            }
            enumMap.put(String.valueOf(e.getCode()), e);
        }
    }

    @Override
    public E convert(String code) {
        E e = enumMap.get(code);
        if (e == null) {
            log.warn("no enum constant {}", code);
        }
        return e;
    }
}
