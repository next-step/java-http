package cache.com.example.cachecontrol;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static cache.com.example.version.CacheBustingWebConfig.ALL_PATH;
import static cache.com.example.version.CacheBustingWebConfig.PREFIX_STATIC_RESOURCES;

@Configuration
public class CacheWebConfig implements WebMvcConfigurer {

    private final static String ALL_RESOURCE_PATH = PREFIX_STATIC_RESOURCES + ALL_PATH;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new CacheControlInterceptor())
                .excludePathPatterns(ALL_RESOURCE_PATH);
    }
}
