package com.silencew.plugins.jpaenums;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by IntelliJ IDEA.
 * author: wangshuiping
 * date: 2021/1/18
 */
@Configuration
@ComponentScan(basePackages = {"com.silencew.plugins.jpaenums"})
public class JsonEnumConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new StringToEnumWrapConverterFactory());
    }
}
