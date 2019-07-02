package com.libs.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @ author：mo
 * @ data：2018/12/19：9:54
 * @ 功能：Fragment基类。主要是一些复写方法
 */
public class BFragment extends Fragment {
    /**
     * 载体activity
     */
    protected Activity mActivity;

    /**
     * 关联activity，不可见
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //获得全局的，防止使用getActivity()为空
        this.mActivity = (Activity) context;
    }

    /**
     * 创建fragment，不可见
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    /**
     * 创建fragment里面的关联view，不可见
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 当Activity中的onCreate方法执行完后调用。即：在这个方法执行之前，前面的三个生命周期方法里不能有ui交互操作，
     * 不可见
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 开始，可见
     */
    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * 恢复，可见
     */
    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 暂停，可见
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * 停止，不可见
     */
    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * 销毁fragment里面的关联view ，不可见
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 销毁 ，不可见
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 解除关联activity，不可见
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }
}
