package cache.com.example.cachecontrol;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

@Configuration
public class CacheWebConfig implements WebMvcConfigurer {

    /**
     * <a href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/WebContentInterceptor.html">공식문서</a>
     * 특정 URL에 대해 캐시 설정을 적용 해주는 Interceptor
     */
    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        WebContentInterceptor webContentInterceptor = new WebContentInterceptor();
        webContentInterceptor.addCacheMapping(CacheControl.noCache().cachePrivate(), "/");
        registry.addInterceptor(webContentInterceptor);
    }
}
