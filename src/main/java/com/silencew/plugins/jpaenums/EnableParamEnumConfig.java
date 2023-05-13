package com.silencew.plugins.jpaenums;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用请求参数@PathVariable,@RequestParam等包含自定义枚举时转换配置
 * Created by IntelliJ IDEA.
 * author: wangshuiping
 * date: 2021/1/19
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(WeFormatGetParamEnumConfig.class)
public @interface EnableParamEnumConfig {
}
