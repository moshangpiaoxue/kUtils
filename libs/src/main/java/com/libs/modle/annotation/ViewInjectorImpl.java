package com.libs.modle.annotation;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.libs.R;
import com.libs.k;
import com.libs.modle.adapter.KRecycleViewAdapter;
import com.libs.modle.annotation.methods.KInjectCheckNet;
import com.libs.modle.annotation.methods.KInjectContentView;
import com.libs.modle.annotation.methods.KInjectEvent;
import com.libs.modle.annotation.methods.KInjectRecycleViewAdapter;
import com.libs.modle.annotation.methods.KInjectView;
import com.libs.modle.listener.clickListener.KNoDoubleClickListener;
import com.libs.modle.viewHolder.KRecycleViewHolder;
import com.libs.utils.logUtils.LogUtil;
import com.libs.utils.temp.NetworkUtil;
import com.libs.utils.tipsUtil.ToastUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;


/**
 * @ author：mo
 * @ data：2019/2/25:13:43
 * @ 功能：view注入实现类
 */
public enum ViewInjectorImpl implements ViewInjector {


    /**
     * 枚举单例
     */
    INSTANCE;
    /**
     * 忽略类，没看懂
     */
    private static final HashSet<Class<?>> IGNORED = new HashSet<Class<?>>();

    static {
        IGNORED.add(Object.class);
        IGNORED.add(Activity.class);
        IGNORED.add(android.app.Fragment.class);
        try {
            IGNORED.add(Class.forName("android.support.v4.app.Fragment"));
            IGNORED.add(Class.forName("android.support.v4.app.FragmentActivity"));
        } catch (Throwable ignored) {
        }
    }

    public void regist() {
        k.Ext.setViewInjector(this);
    }

    @Override
    public void inject(Activity mActivity) {
        Class<?> clazz = mActivity.getClass();
        KInjectContentView kInjectContentView = findContentView(clazz);
        if (kInjectContentView != null) {
            int layoutId = kInjectContentView.value();
            if (layoutId > 0) {
                try {
                    Method setContentView = clazz.getMethod("setContentView", int.class);
                    setContentView.invoke(mActivity, layoutId);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (Throwable e) {
                    LogUtil.i(e.getMessage());
                }
            }
        }
        injectObject(mActivity, clazz, new ViewFinder(mActivity));
    }

    @Override
    public void inject(View view) {
        injectObject(view,view.getClass(),new ViewFinder(view));
    }

    @Override
    public void inject(Object holder, View view) {
        injectObject(holder,holder.getClass(),new ViewFinder(view));
    }

    @Override
    public View inject(Object fragment, LayoutInflater inflater, ViewGroup container) {
        View view=null;
        Class<?> aClass = fragment.getClass();
        KInjectContentView contentView = findContentView(aClass);
        if (contentView!=null){
            int viewId=contentView.value();
            if (viewId>0){
                view=inflater.inflate(viewId,container,false);
            }
        }
        injectObject(fragment,aClass,new ViewFinder(view));
        return view;
    }

    @Override
    public void injectRecycleView(Activity mActivity) {
        Class<?> clazz = mActivity.getClass();
        if (clazz == null || IGNORED.contains(clazz)) {
            return;
        }
        ViewFinder viewFinder = new ViewFinder(mActivity);
        Field[] declaredFields = clazz.getDeclaredFields();
        if (declaredFields != null && declaredFields.length > 0) {
            for (Field field : declaredFields) {
                Class<?> fieldType = field.getType();
                if (
                    //修饰符为static的不注入
                        Modifier.isStatic(field.getModifiers()) ||
                                //修饰符为final的不注入
                                Modifier.isFinal(field.getModifiers()) ||
                                //基本类型字段不注入
                                fieldType.isPrimitive() ||
                                //数组类型不注入
                                fieldType.isArray()) {
                    continue;
                }
                KInjectRecycleViewAdapter adapter = field.getAnnotation(KInjectRecycleViewAdapter.class);
                if (adapter != null) {
                    int value = adapter.value();
                    String date = adapter.date();
                    Field field2 = null;
                    try {
                        field2 = clazz.getDeclaredField(date);
                        field2.setAccessible(true);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                    ArrayList<String> arrayList = null;
                    try {
                        arrayList = (ArrayList<String>) field2.get(mActivity);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    LogUtil.i(arrayList.toString());
                    RecyclerView recyclerView = (RecyclerView) viewFinder.findViewById(adapter.value());
                    KRecycleViewAdapter<String> adapter1 = new KRecycleViewAdapter<String>(mActivity, arrayList) {
                        @Override
                        public void doWhat(KRecycleViewHolder holder, String bean, int position, int itemViewType, RecyclerView parent) {
                            TextView item = holder.getView(R.id.tv_ios_toast);
                            item.setText(bean);
                        }

                        @Override
                        protected int getItemLayout(int viewType) {
                            return R.layout.tost_ios;
                        }
                    };
                    if (adapter1 != null) {
                        recyclerView.setAdapter(adapter1);
                        field.setAccessible(true);
                        try {
                            field.set(mActivity, adapter1);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }
        }
    }


    /**
     * 通用注入
     *
     * @param obj        被注入的实体 activity、fragment，view等
     * @param mClass     当前实体所在的类
     * @param viewFinder findViewById工具
     */
    private void injectObject(Object obj, Class<?> mClass, ViewFinder viewFinder) {
        if (mClass == null || IGNORED.contains(mClass)) {
            return;
        }
        // 从父类到子类递归
        injectObject(obj, mClass.getSuperclass(), viewFinder);
        //mClass.getDeclaredFields()=该类里所有声明的字段
        injectView(obj, mClass, mClass.getDeclaredFields(), viewFinder);
        //mClass.getDeclaredMethods()=该类里所有声明的方法
        injectEvent(obj, mClass.getDeclaredMethods(), viewFinder);
    }

    /**
     * 注入事件
     */
    private void injectEvent(Object obj, Method[] methods, ViewFinder viewFinder) {
        if (methods != null && methods.length > 0) {
            for (Method method : methods) {
                if (
                    //是否使用静态修饰符
                        Modifier.isStatic(method.getModifiers()) ||
                                //是否使用private修饰符
                                Modifier.isPrivate(method.getModifiers())) {
//                    LogUtil.i("当前方法：" + method.getName() + "\n使用了错误的修饰符：static  or private,");
                    continue;
                }
                KInjectEvent onClickAnnotation = method.getAnnotation(KInjectEvent.class);
                if (onClickAnnotation != null) {
                    onClick(obj, method, onClickAnnotation, viewFinder);
                }
            }
        }
    }

    /**
     * 点击事件监听
     */
    private void onClick(final Object obj, final Method method, final KInjectEvent event, ViewFinder viewFinder) {
        final KInjectCheckNet kInjectCheckNet = method.getAnnotation(KInjectCheckNet.class);
        int[] chectNetIds = null;
        if (kInjectCheckNet != null) {
            chectNetIds = kInjectCheckNet.value();
        }
        //获取注解里面要监听的值
        int[] value = event.value();
        for (final int viewId : value) {
            final View view = viewFinder.findViewById(viewId);
            final int[] finalChectNetIds = chectNetIds;
            view.setOnClickListener(new KNoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    if (finalChectNetIds != null) {
                        if (finalChectNetIds.length > 0) {
                            for (int viewId2 : finalChectNetIds) {
                                if (viewId == viewId2) {
                                    if (!NetworkUtil.isAvailable() && kInjectCheckNet.isShow()) {
                                        ToastUtil.showToast(kInjectCheckNet.toast());
                                        return;
                                    }
                                }
                            }
                        } else {
                            if (!NetworkUtil.isAvailable() && kInjectCheckNet.isShow()) {
                                ToastUtil.showToast(kInjectCheckNet.toast());
                                return;
                            }
                        }
                    }


                    //设置私有可用
                    {
                        method.setAccessible(true);
                    }
                    try {
                        //无参构造
                        method.invoke(obj);
                    } catch (Exception e) {
//                        e.printStackTrace();
                        try {
                            //带参构造
                            method.invoke(obj, view);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            });
        }
    }


    /**
     * 注入view
     */
    private void injectView(Object obj, Class<?> mClass, Field[] fields, ViewFinder viewFinder) {
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                Class<?> fieldType = field.getType();
                if (
                    //修饰符为static的不注入
                        Modifier.isStatic(field.getModifiers()) ||
                                //修饰符为final的不注入
                                Modifier.isFinal(field.getModifiers()) ||
                                //基本类型字段不注入
                                fieldType.isPrimitive() ||
                                //数组类型不注入
                                fieldType.isArray()) {
                    continue;
                }
                //找到GetViewById注解
                KInjectView annotation = field.getAnnotation(KInjectView.class);
                //判空，看是否有这个注解
                if (annotation != null) {
                    //拿到注解里设置的id
                    int value = annotation.value();
                    //findViewById拿到view
                    View view = viewFinder.findViewById(annotation.value(), annotation.parentId());
                    try {
                        //设置Sioux属性也可以动态注入，不加这句的话使用private修饰属性的时候会报错
                        field.setAccessible(true);
                        field.set(obj, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    /**
     * 获取类里的 KInjectContentView注解
     */
    private KInjectContentView findContentView(Class<?> clazz) {
        if (clazz == null || IGNORED.contains(clazz)) {
            return null;
        }
        KInjectContentView kInjectContentView = clazz.getAnnotation(KInjectContentView.class);
//        if (kInjectContentView == null) {
//            return findContentView(clazz);
//        }
        return kInjectContentView;
    }
}
