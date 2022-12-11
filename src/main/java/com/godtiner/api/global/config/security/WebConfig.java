package com.godtiner.api.global.config.security;

import com.godtiner.api.domain.notification.NotificationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.StaticResourceLocation;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EnableWebMvc
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final NotificationInterceptor notificationInterceptor;

    @Value("${upload.image.location}")
    private String location;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**") // 1
                .addResourceLocations("file:" + location) // 2
                .setCacheControl(CacheControl.maxAge(Duration.ofHours(1L)).cachePublic()); // 3
    }

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080", "http://localhost:3000") // 허용할 출처
                .allowedMethods("GET", "POST") // 허용할 HTTP method
                .allowCredentials(true) // 쿠키 인증 요청 허용
                .maxAge(3000); // 원하는 시간만큼 pre-flight 리퀘스트를 캐싱
    }

  @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> staticResourcesPath = Stream.of(StaticResourceLocation.values())
                .flatMap(StaticResourceLocation::getPatterns)
                .collect(Collectors.toList());
        staticResourcesPath.add("/node_modules/**");
        registry.addInterceptor(notificationInterceptor)
                .excludePathPatterns(staticResourcesPath);
    }
}