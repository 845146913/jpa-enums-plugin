package com.silencew.plugins.jpaenums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * author: wangshuiping
 * date: 2020/12/30
 */
public interface BaseEnum<E extends Enum<E>, T> {

    /**
     * 请求参数和返回值解析注解
     * @return
     */
    @JsonValue
    T getCode();

    @SuppressWarnings("unchecked")
    default E get() {
        return (E) this;
    }


    @SuppressWarnings("unchecked")
    static <E extends Enum<E> & BaseEnum<E, T>, T> E codeOf(Class<E> clazz, T code) {
        if (null == code || "".equals(code))
            return null;
        int count = 0;
        try {
            for (Field f : clazz.getDeclaredFields()) {
                if (!f.isEnumConstant() && !f.getType().getTypeName().contains(clazz.getName()))
                    count++;
            }
        } catch (SecurityException e1) {
            e1.printStackTrace();
        }
        for (E e : clazz.getEnumConstants()) {
            if (count > 0) {
                E o = e;
                if (Objects.equals(o.getCode(), code))
                    return e;
            }

        }
        throw new IllegalArgumentException(
                "No enum constant " + clazz.getName() + "." + code);
    }

    static <E extends Enum<E> & BaseEnum<E, T>, T> List<T> codes(Class<E> clazz) {
        E[] enumConstants = clazz.getEnumConstants();
        return Arrays.asList(enumConstants).stream().map(E::getCode).collect(Collectors.toList());
    }
}
