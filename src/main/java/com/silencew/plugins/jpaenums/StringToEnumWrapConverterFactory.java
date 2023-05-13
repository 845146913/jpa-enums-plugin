package com.silencew.plugins.jpaenums;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 枚举参数解析类 可以解析@PathVariable,@RequestParam等标记的自定义枚举参数
 * Created by IntelliJ IDEA.
 * author: wangshuiping
 * date: 2020/12/30
 */
public class StringToEnumWrapConverterFactory<E extends BaseEnum<?, String>> implements ConverterFactory<String, E> {
    private final Map<Class<E>, Converter<String, E>> CONVERTERS = new ConcurrentHashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public <T extends E> Converter<String, T> getConverter(Class<T> aClass) {
        Converter<String, E> converter = CONVERTERS.get(aClass);
        if (converter == null) {
            converter = new StringToEnumConverter<>(aClass);
            CONVERTERS.put((Class<E>) aClass, converter);
        }
        return (Converter<String, T>) converter;
    }
}
