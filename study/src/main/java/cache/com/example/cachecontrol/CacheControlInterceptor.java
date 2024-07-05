package cache.com.example.cachecontrol;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class CacheControlInterceptor implements HandlerInterceptor {
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        response.addHeader(HttpHeaders.CACHE_CONTROL, defaultCacheControl().getHeaderValue());
    }

    private CacheControl defaultCacheControl() {
        return CacheControl
                .noCache()
                .cachePrivate();
    }
}
