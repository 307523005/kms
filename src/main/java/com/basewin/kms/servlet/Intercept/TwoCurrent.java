package com.basewin.kms.servlet.Intercept;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@TwoCurrent  第二个用于标识用户实体类入参，参数级注解
public @interface TwoCurrent {
}