package com.libs.utils.viewUtil;


import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.libs.KApplication;
import com.libs.utils.ResUtil;
import com.libs.utils.dataUtil.StringUtil;
import com.libs.utils.dataUtil.dealUtil.ColorUtils;
import com.libs.utils.logUtils.LogUtil;
import com.libs.utils.task.HandlerUtil;

import java.util.Arrays;



/**
 * description: view工具类
 * autour: mo
 * date: 2017/7/23 0023 15:56
 */
public class ViewUtil {
    /***
     * 在指定时间内对指定view完成了指定次数的点击
     * 这个工具类比较适合我们开发人员设置一些隐藏开关，进行特定操作
     * 比如：彻底关闭程序、弹出该页面某些信息等等
     * @param view        要设置点击效果的View
     * @param times       点击的次数
     * @param timeBetween 点击完成规定次数的时间范围
     */
    public static void setClickTimes(View view, final int times, final long timeBetween, final IClick click) {
        //存储多次点击的时间戳
        final long[] mHits = new long[times];
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //自己拷贝自己，只不过错位拷贝【第二个元素拷贝到第一个元素，第一个元素拷贝到第零个元素】  每点击一次就错位一次，便于找到最新的数据
                System.arraycopy(mHits, 1, mHits, 0, times - 1);
                //给数组的最后一个元素赋值
                mHits[times - 1] = SystemClock.uptimeMillis();
//                LogUtil.i(mHits);
                //当第mHits[lengt-1]点击的时间戳减去mHits[0]的时间戳小于指定时间则该多击事件生效
                if (mHits[times - 1] - mHits[0] <= timeBetween) {
                    LogUtil.i(timeBetween + "毫秒内点击" + times + "次");
                    //数据全部置零
                    Arrays.fill(mHits, 0);
                    if (click != null) {
                        //设置事件的回调
                        click.onClickListen(view, timeBetween, times);
                    }
                }
            }
        });
    }

    /**
     * view内容是否为空
     *
     * @param view
     * @return
     */
    public static Boolean isEmpty(View view) {
        String contants = "";
        if (view instanceof TextView) {
            contants = ((TextView) view).getText().toString();
        }
        if (view instanceof EditText) {
            contants = ((EditText) view).getText().toString();
        }
        if (view instanceof Button) {
            contants = ((Button) view).getText().toString();
        }
        return StringUtil.isEmpty(contants);
    }

    /**
     * 通过布局文件获取view
     *
     * @param context  上下文环境
     * @param layoutId 布局id
     * @return view LayoutInflater.from(this).inflate(R.layout.filter, addLayout, false);
     */
    public static View getView(Context context, int layoutId) {
        return LayoutInflater.from(context).inflate(layoutId, null);
    }

    public static View getView(Context context, int layoutId, ViewGroup group) {
        return LayoutInflater.from(context).inflate(layoutId, group, false);
    }


    /**
     * 测量控件的尺寸
     */
    private static void calculateViewMeasure(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
    }

    /**
     * 获取控件的高度
     */
    public static int getViewMeasuredHeight(View view) {
        calculateViewMeasure(view);
        return view.getMeasuredHeight();
    }

    /**
     * 获取控件的宽度
     */
    public static int getViewMeasuredWidth(View view) {
        calculateViewMeasure(view);
        return view.getMeasuredWidth();
    }

    /**
     * 设置某个View的margin
     *
     * @param view   需要设置的view
     * @param left   左边距
     * @param right  右边距
     * @param top    上边距
     * @param bottom 下边距
     * @return ViewGroup.LayoutParams
     */
    public static ViewGroup.LayoutParams setViewMargin(View view, int left, int right, int top, int bottom) {
        if (view == null) {
            return null;
        }

        int leftPx = left;
        int rightPx = right;
        int topPx = top;
        int bottomPx = bottom;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        ViewGroup.MarginLayoutParams marginParams = null;
        //获取view的margin设置参数
        if (params instanceof ViewGroup.MarginLayoutParams) {
            marginParams = (ViewGroup.MarginLayoutParams) params;
        } else {
            //不存在时创建一个新的参数
            marginParams = new ViewGroup.MarginLayoutParams(params);
        }
        //设置margin
        marginParams.setMargins(leftPx, topPx, rightPx, bottomPx);
        view.setLayoutParams(marginParams);
        return marginParams;
    }

    /**
     * 设置view的背景透明度
     *
     * @param view
     * @param aph  0–255之间的数字。数字越大，越不透明。
     */
    public static void setBackgroundAph(View view, int aph) {
        if (aph >= 0 && aph <= 255) {
            //mutate()的作用是通知系统，我的这个改变是独有的不共享，如果没有这个方法，在5.0以上的系统，view所用到的背景图片或者颜色值，在其他控件使用时会一起跟着变
            view.getBackground().mutate().setAlpha(aph);
        }
    }

    /**
     * 获取显示状态====这个状态是是否在屏幕里显示的状态
     *
     * @param activity
     * @param view     要获取状态的控件
     * @return true==还在屏幕里  false==滑出去了，不在屏幕里
     */
    public static Boolean isInWindow(Activity activity, View view) {
        Point p = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(p);
        int screenWidth = p.x;
        int screenHeight = p.y;
        Rect rect = new Rect(0, 0, screenWidth, screenHeight);
        int[] location = new int[2];
        view.getLocationInWindow(location);
        if (view.getLocalVisibleRect(rect)) {
//            LogUtil.i("看见了");
            return true;
        } else {
//            LogUtil.i("看不见了");
            return false;
        }
    }

    public static int[] getViewSize(final View view) {
        final int[] aa = new int[2];
        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                aa[0] = view.getWidth();
                aa[1] = view.getHeight();
            }
        });
        return aa;
    }

    /**
     * 获取指定的 View 再屏幕中的位置
     * <p>该位置为 View 左上角像素相对于屏幕左上角的位置
     *
     * @param view View
     * @return 包含 x 和 y 坐标的数组, [0] 为 x 坐标, [1] 为 y 坐标
     */
    public static int[] getLocationOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

    /**
     * 获取指定的 View 再屏幕中的位置, 分别为: 左/上/右/下
     *
     * @param view View
     * @return 包含该 View 左上右下在屏幕中的位置
     */
    public static int[] getLocationsOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int[] locations = new int[4];
        locations[0] = location[0];
        locations[1] = location[1];
        locations[2] = location[0] + view.getWidth();
        locations[3] = location[1] + view.getHeight();
        return locations;
    }

    /**
     * 获取子view在父view中的位置
     *
     * @param view   子view
     * @param parent 父view
     * @return
     */
    public static int getIndexForViewInParent(View view, ViewGroup parent) {
        int index;
        for (index = 0; index < parent.getChildCount(); index++) {
            if (parent.getChildAt(index) == view) {
                break;
            }
        }
        return index;
    }

    /**
     * view是否被遮挡
     *
     * @param view
     * @return
     */
    public static boolean isViewCovered(final View view) {
        View currentView = view;
        Rect currentViewRect = new Rect();
        boolean partVisible = currentView.getGlobalVisibleRect(currentViewRect);
        boolean totalHeightVisible = (currentViewRect.bottom - currentViewRect.top) >= view.getMeasuredHeight();
        boolean totalWidthVisible = (currentViewRect.right - currentViewRect.left) >= view.getMeasuredWidth();
        boolean totalViewVisible = partVisible && totalHeightVisible && totalWidthVisible;
        // if any part of the view is clipped by any of its parents,return true
        if (!totalViewVisible) {
            return true;
        }

        while (currentView.getParent() instanceof ViewGroup) {
            ViewGroup currentParent = (ViewGroup) currentView.getParent();
            // if the parent of view is not visible,return true
            if (currentParent.getVisibility() != View.VISIBLE) {
                return true;
            }

            int start = getIndexForViewInParent(currentView, currentParent);
            for (int i = start + 1; i < currentParent.getChildCount(); i++) {
                Rect viewRect = new Rect();
                view.getGlobalVisibleRect(viewRect);
                View otherView = currentParent.getChildAt(i);
                Rect otherViewRect = new Rect();
                otherView.getGlobalVisibleRect(otherViewRect);
                // if view intersects its older brother(covered),return true
                if (Rect.intersects(viewRect, otherViewRect)) {
                    return true;
                }
            }
            currentView = currentParent;
        }
        return false;
    }


    /**
     * 设置根布局参数
     * 使用场景：状态栏操作
     * android:clipToPadding="true"
     * android:fitsSystemWindows="true"
     */
    public static void setViewfits(Activity activity, Boolean isTrue) {
        ViewGroup parent = (ViewGroup) activity.findViewById(android.R.id.content);
        for (int i = 0, count = parent.getChildCount(); i < count; i++) {
            View childView = parent.getChildAt(i);
            if (childView instanceof ViewGroup) {
                childView.setFitsSystemWindows(isTrue);
                ((ViewGroup) childView).setClipToPadding(isTrue);
            }
        }
    }

    public static void setViewfits(View view, Boolean isTrue) {
        view.setFitsSystemWindows(isTrue);
        ((ViewGroup) view).setClipToPadding(isTrue);
    }

    /**
     * 生成新view
     *
     * @param activity   载体
     * @param color      颜色
     * @param alpha      透明度
     * @param viewId     id
     * @param viewHeight 高度
     * @param viewWidth  宽度
     * @return
     */
    public static View getView(Activity activity, @ColorInt int color, @IntRange(from = 0, to = 255) int alpha, int viewId, int viewHeight, int viewWidth) {
        // 绘制一个和状态栏一样高的矩形
        View view = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(viewHeight, viewWidth);
        view.setLayoutParams(params);
        view.setBackgroundColor(color == 0 ? ResUtil.getColor("#FFFFFF") : ColorUtils.getColor(color, alpha));
        view.setId(viewId);
        return view;
    }

    /**
     * 根据背景图片生成view
     *
     * @param activity
     * @param drawable
     * @param viewId
     * @param viewHeight
     * @param viewWidth
     * @return
     */
    public static View getView(Activity activity, Drawable drawable, int viewId, int viewHeight, int viewWidth) {
        // 绘制一个和状态栏一样高的矩形
        View view = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(viewHeight, viewWidth);
        view.setLayoutParams(params);
        view.setBackground(drawable);
        view.setId(viewId);
        return view;
    }

    /**
     * 设置提示内容
     *
     * @param tipsView
     * @param tips
     */
    public static void tipsView(final TextView tipsView, String tips) {
        tipsView.setText(tips);
        if (tipsView.getVisibility() != View.VISIBLE) {
            tipsView.setVisibility(View.VISIBLE);
            KApplication.getMainHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
//                    mActivity.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
                    tipsView.setVisibility(View.INVISIBLE);
//                        }
//                    });

                }
            }, 1200);
        }
    }

    public interface IClick {
        void onClickListen(View view, final long timeBetween, final int times);
    }


    /**
     * 为指定的 View 及其所有子 View 都设置 selected 状态
     *
     * @param view     指定 View
     * @param selected 是否为 selected
     */
    public static void setSelectedWithChildView(View view, boolean selected) {
        if (view == null) {
            return;
        }
        view.setSelected(selected);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                setSelectedWithChildView(viewGroup.getChildAt(i), selected);
            }
        }
    }

    /**
     * 扩大 View 的触摸和点击响应范围, 最大不超过其父View范围
     *
     * @param view   View
     * @param top    顶部扩大的像素值
     * @param bottom 底部扩大的像素值
     * @param left   左边扩大的像素值
     * @param right  右边扩大的像素值
     */
    public static void expandViewTouchDelegate(final View view, final int top,
                                               final int bottom, final int left, final int right) {
        view.post(new Runnable() {
            @Override
            public void run() {
                if (View.class.isInstance(view.getParent())) {
                    Rect bounds = new Rect();
                    view.setEnabled(true);
                    view.getHitRect(bounds);
                    bounds.top -= top;
                    bounds.bottom += bottom;
                    bounds.left -= left;
                    bounds.right += right;
                    ((View) view.getParent()).setTouchDelegate(new TouchDelegate(bounds, view));
                }
            }
        });
    }

    /**
     * 还原 View 的触摸和点击响应范围, 最小不小于 View 自身范围
     *
     * @param view View
     */
    public static void restoreViewTouchDelegate(final View view) {
        view.post(new Runnable() {
            @Override
            public void run() {
                if (View.class.isInstance(view.getParent())) {
                    ((View) view.getParent()).setTouchDelegate(new TouchDelegate(new Rect(), view));
                }
            }
        });
    }

    /**
     * 隐藏menu的长按效果
     *
     * @param activity
     * @param id
     */
    public static void hideMenuToast(final Activity activity, final int id) {
        /* 处理item长按弹出Toast Title信息*/
//        new Handler().post(() -> {
//            final View v = activity.findViewById(id);
//            if (v != null) {
//                v.setOnLongClickListener(v1 -> false);
//            }
//        });
        HandlerUtil.postRunnable(new Runnable() {
            @Override
            public void run() {
                final View v = activity.findViewById(id);
                if (v != null) {
                    v.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            return false;
                        }
                    });
                }
            }
        });
    }
}
