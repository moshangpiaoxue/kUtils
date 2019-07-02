package com.libs.modle.annotation.methods;

import android.support.annotation.LayoutRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ author：mo
 * @ data：2019/2/25:18:06
 * @ 功能：类布局注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface KInjectContentView {
    @LayoutRes
    int value();
}
