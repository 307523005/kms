package com.basewin.kms.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
//@ResponseBody
public class GlobalExceptionHandler {
    private static Log log = LogFactory.getLog(GlobalExceptionHandler.class);

    /**
     * 全局异常处理
     *
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    public String allExceptionHandler(HttpServletRequest request,
                                      Exception exception) throws Exception {
        exception.printStackTrace();
        log.error("输出本地化的描述信息：" + exception.getLocalizedMessage());
        log.error("返回此异常的原因：" + exception.getCause());//此 throwable 的 cause，如果 cause 不存在或是未知的，则返回 null。
        log.error("获得被屏蔽的异常：" + exception.getSuppressed());
        log.error("输出异常的描述信息：" + exception.getMessage());
        log.error("将异常栈打印到输出流中：" + exception.getStackTrace());
        return "/error/error";//返回404页面
    }

}