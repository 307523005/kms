package com.basewin.kms.servlet.Intercept;

import com.basewin.kms.entity.SMKeyBeen;
import com.basewin.kms.service.SSLService;
import com.basewin.kms.servlet.redis.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 添加拦截器 AuthInterceptor 继承字 HandlerInterceptorAdapter 重写 preHandle 方法
 * 所有请求先进来这里
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {
    protected static Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
    @Autowired
    private SSLService sSLService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {




        logger.info("--进入拦截器AuthInterceptor--");
        if (!HandlerMethod.class.isAssignableFrom(handler.getClass())) {
            logger.info("--进入拦截器1,是用来判断一个类Class1和另一个类Class2是否相同或是另一个类的子类或接口。 ");
           //跳转登陆
            response.sendRedirect("/ssl/login");
            return false;
        }

        // 如果不是映射到方法直接通过
       /* if (!(handler instanceof HandlerMethod)) {
            log.info("--进入拦截器，但该方法不需要拦截,用来判断一个对象实例是否是一个类或接口的或其子类子接口的实例。 ");
            return true;
        }*/

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        String requestPath = request.getRequestURI();
        // log.debug("requestIp: " + getIpAddress(request));
        //log.debug("Method: " + method.getName() + ", OenIntercept: " + method.isAnnotationPresent(OenIntercept.class));
        //log.debug("requestPath: " + requestPath);
        if (!handlerMethod.hasMethodAnnotation(OenIntercept.class) && handlerMethod.getBeanType().getAnnotation(OenIntercept.class) == null && !handlerMethod.hasMethodAnnotation(TwoIntercept.class) && handlerMethod.getBeanType().getAnnotation(TwoIntercept.class) == null) {
            logger.info("--进入拦截器,类和方法上都没有拦截注解");
            return true;
        }
        if (requestPath.contains("/111") || requestPath.contains("/swagger") || requestPath.contains("/configuration/ui")) {
            logger.info("--1123");
            return true;
        }
        if (requestPath.contains("/error")) {
            logger.info("--1111333");
            return true;
        }

        if (method.isAnnotationPresent(OenIntercept.class) || handlerMethod.hasMethodAnnotation(OenIntercept.class)) {
            logger.info("---OenIntercept----");
            String token = request.getHeader("ACCESS_TOKEN");
            token = "niuhao";
            logger.info("token: " + token);
            if (token.equals("") || token == null) {
                throw new Exception("无效token");
            }
            SMKeyBeen ssLs = sSLService.getSSLs(token);
            logger.info(String.valueOf(ssLs.getId()));
            request.setAttribute("currentUser", ssLs);
            RedisUtils redisUtils = WebApplicationContextUtils
                    .getWebApplicationContext(request.getServletContext()).getBean(RedisUtils.class);
            redisUtils.set("niuhao", ssLs);
            return true;
        }
        if (method.isAnnotationPresent(TwoIntercept.class) || handlerMethod.hasMethodAnnotation(TwoIntercept.class)) {
            logger.info("---TwoIntercept----");
            String token = request.getHeader("ACCESS_TOKEN");
            token = "niuhao";
            logger.debug("token: " + token);
            if (token.equals("") || token == null) {
                throw new Exception("无效token");
            }
            SMKeyBeen ssLs = sSLService.getSSLs(token);
            ssLs.setId(123456);
            request.setAttribute("TwoCurrent", ssLs);

            return true;
        }
        logger.info("-离开拦截器");
        return true;
    }
}
/*Class.isAssignableFrom()方法 与 instanceof 关键字的区别

1. Class.isAssignableFrom()
是用来判断一个类Class1和另一个类Class2是否相同或是另一个类的子类或接口。
格式为： Class1.isAssignableFrom(Class2)
调用者和参数都是java.lang.Class类型, 返回值：boolean类型

以：Object，String为例
a. "String是Object的父类"
	String.class.isAssignableFrom(Object.class) 结果false；

b. "Object是String的父类"
	Object.class.isAssignableFrom(String.class) 结果true；

c. "Object和Object相同"
	Object.class.isAssignableFrom(Object.class) 结果true；


2. instanceof
用来判断一个对象实例是否是一个类或接口的或其子类子接口的实例。
格式是：o instanceof TypeName
第一个参数是对象实例名，第二个参数是具体的类名或接口名。

以：Object，String为例
String str = new String();
String obj = new Object();

a. "Object是String的父类"
	str instanceof Object 结果true；

b. "String和String相同"
	str instanceof String 结果true；

*/