package cache.com.example.version;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;

@Configuration
public class CacheBustingWebConfig implements WebMvcConfigurer {

    public static final String PREFIX_STATIC_RESOURCES = "/resources";

    private final ResourceVersion version;

    @Autowired
    public CacheBustingWebConfig(ResourceVersion version) {
        this.version = version;
    }

    /**
     * <a href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/config/annotation/ResourceHandlerRegistry.html">공식문서</a>
     * ResourceHandlerRegistry
     * - 이미지, css파일 및 기타 정적 리소스를 제공하기 위한 리소스 핸들러를 등록하는 클래스
     */
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler(PREFIX_STATIC_RESOURCES + "/" + version.getVersion() + "/**")  // 정적 리소스를 제공하기 위한 path pattern
                .setEtagGenerator(resource -> version.getVersion())  // ETag를 버전으로 생성
                .setCacheControl(CacheControl.maxAge(Duration.ofDays(365)).cachePublic())  // 캐시 기간 1년
                .addResourceLocations("classpath:/static/");
    }
}
