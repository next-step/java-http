package cache.com.example.etag;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@Configuration
public class EtagFilterConfiguration {

    /**
     * <a href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/filter/ShallowEtagHeaderFilter.html">공식문서</a>
     * <a href="https://www.baeldung.com/etags-for-rest-with-spring">벨덩~</a>
     * - ETag가 응답 콘텐츠를 기반으로 하기 때문에 응답이 계속 렌더링됨  ??
     * - ETag 적용이 필요한 요청은 표준 요청으로 처리되며, 사용하는 리소스(DB 커넥션 등)를 모두 사용하며, 클라이언트에 응답이 반환되기 전에만 ETag 지원이 적용된다는 뜻
     * - 그 시점에서 ETag가 response body에서 계산되어 리소스 자체에 설정됨
     * - 이 외에도 ResponseEntity 에서도 etag 설정을 지원함
     */
    @Bean
    public FilterRegistrationBean<ShallowEtagHeaderFilter> shallowEtagHeaderFilter() {
        FilterRegistrationBean<ShallowEtagHeaderFilter> filter = new FilterRegistrationBean<>();
        ShallowEtagHeaderFilter shallowEtagHeaderFilter = new ShallowEtagHeaderFilter();
        filter.setFilter(shallowEtagHeaderFilter);
        filter.addUrlPatterns("/etag");
        return filter;
    }
}
