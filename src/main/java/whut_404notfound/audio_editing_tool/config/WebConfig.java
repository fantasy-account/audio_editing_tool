package whut_404notfound.audio_editing_tool.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import whut_404notfound.audio_editing_tool.interceptor.CorsFilter;
import whut_404notfound.audio_editing_tool.interceptor.CorsInterceptor;
import whut_404notfound.audio_editing_tool.interceptor.LoginInterceptor;

/**
 * Web配置类
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/3/13
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/login", "/error");
    }

    @Bean
    public FilterRegistrationBean corsFilter(){
        return new FilterRegistrationBean(new CorsFilter());
    }

}
