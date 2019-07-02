package com.libs.modle.annotation.methods;

import android.support.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ author：mo
 * @ data：2019/2/25:11:05
 * @ 功能：点击事件注解
 * 方法名固定 private void onClick(View view) {}
 */
//运行时生效
@Retention(RetentionPolicy.RUNTIME)
//对方法生效
@Target(ElementType.METHOD)
public @interface KInjectEvent {
    @IdRes
    int[] value();
}
