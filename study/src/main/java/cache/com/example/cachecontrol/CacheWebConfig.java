package cache.com.example.cachecontrol;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CacheWebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new CacheControlInterceptor());
    }

    private class CacheControlInterceptor implements HandlerInterceptor {

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
            if (request.getRequestURI().startsWith("/")) {
                response.setHeader("Cache-Control", "no-cache, private");
            }

            if (request.getRequestURI().endsWith(".css") || request.getRequestURI().endsWith(".js")) {
                response.setHeader("Cache-Control", "max-age=31536000, public");
            }
            HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        }
    }
}
