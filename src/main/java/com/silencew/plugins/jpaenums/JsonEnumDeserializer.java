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
public class JsonEnumDeserializer extends JsonDeserializer<BaseEnum<?,?>> implements ContextualDeserializer {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private Class<BaseEnum<?,?>> clazz;

    /**
     * ctx.getContextualType() 获取不到类信息
     */
    @Override
    public BaseEnum<?,?> deserialize(JsonParser jsonParser, DeserializationContext ctx) throws IOException {
        Class<BaseEnum<?,?>> enumType = clazz;
        if (Objects.isNull(enumType) || !BaseEnum.class.isAssignableFrom(enumType)) {
            return null;
        }
        String text = jsonParser.getText();
        BaseEnum[] enumConstants = enumType.getEnumConstants();

        // 将值与枚举对象对应并缓存
        for (BaseEnum e : enumConstants) {
            try {
                if (Objects.equals(String.valueOf(e.getCode()), text)) {
                    return (BaseEnum<?,?>) e;
                }
            } catch (Exception ex) {
                log.error("获取枚举值错误!!! ", ex);
            }
        }
        return null;
    }

    /**
     * 为不同的枚举获取合适的解析器
     *
     * @param ctx      ctx
     * @param property property
     */
    @Override
    public JsonDeserializer<BaseEnum<?,?>> createContextual(DeserializationContext ctx, BeanProperty property) {
        Class<?> rawCls = ctx.getContextualType().getRawClass();
        JsonEnumDeserializer converter = new JsonEnumDeserializer();
        converter.setClazz(rawCls);
        return converter;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = (Class<BaseEnum<?,?>>) clazz;
    }
}
