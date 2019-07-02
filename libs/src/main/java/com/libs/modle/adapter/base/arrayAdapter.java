package com.libs.modle.adapter.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * author：mo
 * data：2017/11/13 0013
 * 功能：arrayAdapter
 */

public class arrayAdapter<T> extends android.widget.ArrayAdapter<T> {

    /**
     * @param context  上下文
     * @param resource 布局文件
     * @param objects  填充数据 数组
     */
    public arrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull T[] objects) {
        super(context, resource, objects);
    }

    /**
     * @param context  上下文
     * @param resource 布局文件
     * @param objects  填充数据 list
     */
    public arrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<T> objects) {
        super(context, resource, objects);
    }
}
