package com.basewin.kms.servlet.Intercept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 注册拦截器和参数解析器在 WebMvcConfigurerAdapter 中，需要注意，装配拦截器@Autowired
 *
 *springboot2.0  继承WebMvcConfigurerAdapter已经过时改用 implements WebMvcConfigurer 接口
 *
 */
@Configuration
public class MyWebMvcConfigurerAdapter implements  WebMvcConfigurer {
    //关键，将拦截器装配
    @Autowired
    private AuthInterceptor myAuthInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
     /*   InterceptorRegistration ir = registry.addInterceptor(myAuthInterceptor());
        // 配置不拦截的路径
        ir.excludePathPatterns("/RemotecontrolController/*");
        // 配置拦截的路径
        ir.addPathPatterns("/**");*/
        registry.addInterceptor(myAuthInterceptor).addPathPatterns("/ssl/**").excludePathPatterns("/RemotecontrolController/**","/static/**");
        // 还可以在这里注册其它的拦截器
        // registry.addInterceptor(new OtherInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //第一个参数解析器
        argumentResolvers.add(oenCurrentMethodArgumentResolver());
        //第二个参数解析器
        argumentResolvers.add(twoCurrentUserMethodArgumentResolver());
    }

    @Bean//第一个参数解析器
    public OenCurrentMethodArgumentResolver oenCurrentMethodArgumentResolver() {
        return new OenCurrentMethodArgumentResolver();
    }

    @Bean//第二个参数解析器
    public TwoCurrentMethodArgumentResolver twoCurrentUserMethodArgumentResolver() {
        return new TwoCurrentMethodArgumentResolver();
    }

}