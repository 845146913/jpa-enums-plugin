package com.silencew.plugins.jpaenums;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.Objects;

/**
 * RequestBody自定义枚举参数解析器
 * 配合@JsonDeserialize(using = JsonEnumDeserializer.class)添加到实体属性上使用
 * Created by IntelliJ IDEA.
 * author: wangshuiping
 * date: 2020/12/31
 */
@JsonComponent
public class JsonEnumDeserializer<E extends Enum<E> & BaseEnum<E, T>, T> extends JsonDeserializer<E> implements ContextualDeserializer {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private Class<E> clazz;

    /**
     * ctx.getContextualType() 获取不到类信息
     */
    @Override
    public E deserialize(JsonParser jsonParser, DeserializationContext ctx) throws IOException {
        Class<E> enumType = clazz;
        if (Objects.isNull(enumType) || !enumType.isEnum()) {
            log.info("Not enum maybe! :{}", enumType.getCanonicalName());
            return null;
        }
        String text = jsonParser.getText();
        return BaseEnum.class.isAssignableFrom(enumType) ?
                getE(enumType, text, false) : getE(enumType, text, true);
    }

    private E getE(Class<E> enumType, String text, boolean isSuperEnumClz) {
        E[] enumConstants = enumType.getEnumConstants();

        for (E e : enumConstants) {
            try {
                String idx = isSuperEnumClz ? String.valueOf(e.ordinal()) : String.valueOf(e.getCode());
                if (Objects.equals(idx, text)) {
                    return e;
                }
            } catch (Exception ex) {
            }
        }
        return Enum.valueOf(enumType, text);
    }

    /**
     * 为不同的枚举获取合适的解析器
     *
     * @param ctx      ctx
     * @param property property
     */
    @Override
    public JsonDeserializer<E> createContextual(DeserializationContext ctx, BeanProperty property) {
        Class<?> rawCls = ctx.getContextualType().getRawClass();
        JsonEnumDeserializer converter = new JsonEnumDeserializer();
        converter.setClazz(rawCls);
        return converter;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = (Class<E>) clazz;
    }
}
