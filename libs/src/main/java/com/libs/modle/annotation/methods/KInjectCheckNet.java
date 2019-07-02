package com.libs.modle.annotation.methods;

import android.support.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ author：mo
 * @ data：2019/2/25:16:47
 * @ 功能：检查网络注解，配合点击注解使用
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface KInjectCheckNet {
    //需要检查网络的viewid，默认没有，也就是全部都需要检查
    @IdRes
    int[] value() default {};
    //吐司提示信息
    String toast() default "网络异常，请检查网络";
    // 是否弹出吐司，默认是
    boolean isShow() default true;
}
