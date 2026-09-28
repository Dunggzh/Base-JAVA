package com.its.base_java.configuration;

import com.its.base_java.component.converter.EnumConverterFactory;
import com.its.base_java.security.interceptor.AdminInterceptor;
import com.its.base_java.security.interceptor.TrackLogRequestInterceptor;
import com.its.base_java.security.interceptor.UserInterceptor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.*;


@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final UserInterceptor userInterceptor;
    private final AdminInterceptor adminInterceptor;
    private final TrackLogRequestInterceptor trackLogRequestInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        final long MAX_AGE_SECS = 3600;
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
                .maxAge(MAX_AGE_SECS);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(trackLogRequestInterceptor).addPathPatterns("/**");
        registry.addInterceptor(adminInterceptor).addPathPatterns("/api/admin/v*/**")
                .excludePathPatterns("/api/admin/v*/auth/**", "/api/admin/v*/block/webhook/receive","/api/admin/v*/ticket/webhook/receive","/api/admin/v*/statistic/total-ccu","/api/admin/v*/statistic/total-status-ccu");
        registry.addInterceptor(userInterceptor).addPathPatterns("/api/v*/**")
                .excludePathPatterns("/api/v*/auth/**", "/api/v*/mobile-id/**",
                        "/api/v*/media/upload-image", "/api/v*/sepay/webhook/**", "/api/v*/sse/**");
    }

    @Override
    public void configureContentNegotiation(@NotNull ContentNegotiationConfigurer configurer) {
        WebMvcConfigurer.super.configureContentNegotiation(configurer);
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new EnumConverterFactory());
    }
}