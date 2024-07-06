package cache.com.example.version;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;

@Configuration
public class CacheBustingWebConfig implements WebMvcConfigurer {

    public static final String PREFIX_STATIC_RESOURCES = "/resources";
    public static final String ALL_PATH = "/**";
    private static final String RESOURCE_LOCATION = "classpath:/static/";
    private static final Duration CACHE_MAXAGE = Duration.ofDays(365);

    private final VersionHandlebarsHelper versionHandlebarsHelper;

    public CacheBustingWebConfig(VersionHandlebarsHelper versionHandlebarsHelper) {
        this.versionHandlebarsHelper = versionHandlebarsHelper;
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler(versionHandlebarsHelper.staticUrls(ALL_PATH))
                .addResourceLocations(RESOURCE_LOCATION)
                .setCacheControl(CacheControl.maxAge(CACHE_MAXAGE).cachePublic());
    }
}
