package com.silencew.plugins.jpaenums;

import javax.persistence.AttributeConverter;
import java.lang.reflect.ParameterizedType;

/**
 * 通用数据库枚举转换
 * Created by IntelliJ IDEA.
 * author: wangshuiping
 * date: 2020/12/30
 */
public abstract class BaseEnumConverter<E extends Enum<E> & BaseEnum<E, T>, T> implements AttributeConverter<E, T> {

    @Override
    public T convertToDatabaseColumn(E e) {
        return e.getCode();
    }

    @Override
    @SuppressWarnings("unchecked")
    public E convertToEntityAttribute(T y) {
        Class<E> enumClz = (Class<E>) (((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments())[0];
        return BaseEnum.codeOf(enumClz, y);
    }
}
