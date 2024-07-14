package cache.com.example.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class ResourceCacheInterceptor implements HandlerInterceptor {
    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws Exception {
        response.addHeader("Cache-Control", String.format("max-age=%d, public", 60 * 60 * 24 * 365));
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
