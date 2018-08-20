package com.basewin.kms.servlet.Intercept;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@OenCurrent 用于标识用户实体类入参，参数级注解
public @interface OenCurrent {
}