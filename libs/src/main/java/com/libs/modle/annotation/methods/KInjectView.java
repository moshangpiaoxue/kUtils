package com.libs.modle.annotation.methods;

import android.support.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ author：mo
 * @ data：2019/2/25:9:55
 * @ 功能：通过id获取view 等同findViewById()
 */

//CLASS 编译时注解  RUNTIME运行时注解 SOURCE 源码注解
@Retention(RetentionPolicy.RUNTIME)
//注解作用范围:FIELD 属性  METHOD方法  TYPE 放在类上
@Target(ElementType.FIELD)
//@interface则是表明这个类是一个注解
public @interface KInjectView {
    //表示@KInjectView() 注解时，括号里面的编写的为int类型的值
    @IdRes
    int value();
    //父view id
    int parentId() default 0;
}
