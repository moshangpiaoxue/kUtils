package com.libs.ui.activity;

import android.os.Bundle;

import com.libs.R;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

/**
 * @ author：mo
 * @ data：2018/1/29：15:49
 * @ 功能：Activity右滑关闭页面基类
 */
public class KSlidingCloseActivity extends BActivity
//        implements BGASwipeBackHelper.Delegate
{
    /**
     * 帮助类
     */
//    protected BGASwipeBackHelper mSwipeBackHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回」
        // 在 super.onCreate(savedInstanceState) 之前调用该方法
//        initSwipeBackFinish();

        super.onCreate(savedInstanceState);
    }

//    /**
//     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
//     */
//    private void initSwipeBackFinish() {
//        if (isInitSwipe()) {
//            mSwipeBackHelper = new BGASwipeBackHelper(this, this);
//
//            // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回」
//            // 下面几项可以不配置，这里只是为了讲述接口用法。
//
//            // 设置滑动返回是否可用。默认值为 true
//            mSwipeBackHelper.setSwipeBackEnable(false);
//            // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
//            mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
//            // 设置是否是微信滑动返回样式。默认值为 true
//            mSwipeBackHelper.setIsWeChatStyle(true);
//            // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
//            mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
//            // 设置是否显示滑动返回的阴影效果。默认值为 true
//            mSwipeBackHelper.setIsNeedShowShadow(true);
//            // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
//            mSwipeBackHelper.setIsShadowAlphaGradient(true);
//            // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
//            mSwipeBackHelper.setSwipeBackThreshold(0.3f);
//        }
//    }
//
//    /**
//     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
//     *
//     * @return
//     */
//    @Override
//    public boolean isSupportSwipeBack() {
//        return true;
//    }
//
//    public boolean isInitSwipe() {
//        return false;
//    }
//
//    /**
//     * 正在滑动返回
//     *
//     * @param v 从 0 到 1
//     */
//    @Override
//    public void onSwipeBackLayoutSlide(float v) {
//
//    }
//
//    /**
//     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
//     */
//    @Override
//    public void onSwipeBackLayoutCancel() {
//
//    }
//
//    /**
//     * 滑动返回执行完毕，销毁当前 Activity
//     */
//    @Override
//    public void onSwipeBackLayoutExecuted() {
//        mSwipeBackHelper.swipeBackward();
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (mSwipeBackHelper != null) {
//            // 正在滑动返回的时候取消返回按钮事件
//            if (mSwipeBackHelper.isSliding()) {
//                return;
//            }
//            mSwipeBackHelper.backward();
//        } else {
//            super.onBackPressed();
//        }
//
//    }

}
