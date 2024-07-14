package cache.com.example.cachecontrol;

import cache.com.example.interceptor.DefaultCacheInterceptor;
import cache.com.example.interceptor.ResourceCacheInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CacheWebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new DefaultCacheInterceptor()).excludePathPatterns("/resources/**");
        registry.addInterceptor(new ResourceCacheInterceptor()).addPathPatterns("/resources/**");
    }
}
