package com.szyooge.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.szyooge.interceptor.WeChatUrlImterceptor;

/**
 * WEB配置
 * @ClassName: WebAppConfigurer
 * @author quanyou.chen
 * @date: 2017年7月27日 下午12:53:54
 * @version  v 1.0
 */
@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new WeChatUrlImterceptor())
            .addPathPatterns("/wxHttpGet")
            .addPathPatterns("/wxHttpPost");
        super.addInterceptors(registry);
    }
}
