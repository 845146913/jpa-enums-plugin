package com.silencew.plugins.jpaenums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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


    static <E extends BaseEnum<?, T>, T> E codeOf(Class<E> clazz, T code) {
        return codeOfOptional(clazz, code).orElse(null);
    }
    static <E extends BaseEnum<?, T>, T> Optional<E> codeOfOptional(Class<E> clazz, T code) {
        if (null == code || "".equals(code))
            return Optional.empty();
        for (E e : clazz.getEnumConstants()) {
            if (Objects.equals(e.getCode(), code))
                return Optional.of(e);
        }
        return Optional.empty();
    }

    static <E extends Enum<E> & BaseEnum<E, T>, T> List<T> codes(Class<E> clazz) {
        E[] enumConstants = clazz.getEnumConstants();
        return Arrays.stream(enumConstants).map(E::getCode).collect(Collectors.toList());
    }
}
