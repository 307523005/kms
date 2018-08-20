package com.basewin.kms.servlet.Intercept;

import com.basewin.kms.entity.SMKeyBeen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

//     添加参数解析器 OenCurrentMethodArgumentResolver
public class TwoCurrentMethodArgumentResolver implements HandlerMethodArgumentResolver {
    protected static Logger logger = LoggerFactory.getLogger(TwoCurrentMethodArgumentResolver.class);
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        logger.info("--TwoCurrentMethodArgumentResolver--");
        return parameter.getParameterType().isAssignableFrom(SMKeyBeen.class)
                && parameter.hasParameterAnnotation(TwoCurrent.class);
    }
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        SMKeyBeen userInfo = (SMKeyBeen) webRequest.getAttribute("TwoCurrent", RequestAttributes.SCOPE_REQUEST);
        if (userInfo != null) {
            logger.info(userInfo.getId()+"----");
            return userInfo;
        }
        throw new MissingServletRequestPartException("TwoCurrent");
    }
}