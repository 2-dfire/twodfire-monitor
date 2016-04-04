package com.twodfire.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * User: edagarli
 * Email: lizhi@edagarli.com
 * Date: 2015/6/5
 * Time: 14:36
 * Desc: 在方法名上添加了下面的 annotation，就可以进行相关的东西拦截了。
 */

@java.lang.annotation.Target({ElementType.METHOD})
@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
public @interface MethodAnnotation {
}
