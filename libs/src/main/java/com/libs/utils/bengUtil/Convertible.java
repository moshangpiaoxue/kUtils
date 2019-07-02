package com.libs.utils.bengUtil;

/**
 * @ author：mo
 * @ data：2019/2/15:10:46
 * @ 功能：
 */
public interface Convertible<F, T> {

    T convert(F from);
}
