package com.libs.modle.annotation;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @ author：mo
 * @ data：2019/2/25:13:42
 * @ 功能：view注入接口
 */
public interface ViewInjector {
    /**
     * 注入activity
     *
     * @param activity
     */
    void inject(Activity activity);

    /**
     * 注入view
     */
    void inject(View view);

    /**
     * 注入ViewHolder
     */
    void inject(Object holder, View view);

    /**
     * 注入碎片
     */
    View inject(Object fragment, LayoutInflater inflater, ViewGroup container);

    /**
     * 注入activity 对recycleView起作用（测试）
     */
    void injectRecycleView(Activity mActivity);
}
