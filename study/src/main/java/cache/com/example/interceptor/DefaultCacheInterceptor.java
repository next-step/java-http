package cache.com.example.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class DefaultCacheInterceptor implements HandlerInterceptor {
    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws Exception {
        response.addHeader("Cache-Control", "no-cache, private");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
