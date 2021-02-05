package com.silencew.plugins.jpaenums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求参数@RequestBody等包含自定义枚举时转换注解
 * PS: 请使用@JsonDeserialize(using = JsonEnumDeserializer.class)，该注解暂可不使用
 * Created by IntelliJ IDEA.
 * author: wangshuiping
 * date: 2021/1/19
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@JsonDeserialize(using = JsonEnumDeserializer.class)
public @interface JsonEnumDeserialze {
}
