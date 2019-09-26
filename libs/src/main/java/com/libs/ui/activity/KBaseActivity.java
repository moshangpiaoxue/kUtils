package com.libs.ui.activity;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.libs.modle.viewHolder.ViewHolder;
import com.libs.utils.appUtils.barUtils.StatusBarUtils;
import com.libs.utils.bengUtil.NextActivityUtil;
import com.libs.utils.logUtils.LogUtil;
import com.libs.utils.systemUtils.KeyBordUtil;
import com.libs.utils.systemUtils.ScreenUtil;
import com.libs.utils.tipsUtil.ToastUtil;


/**
 * description: 基础activity
 * autour: mo
 * date: 2017/11/14 0014 14:44
 */
public abstract class KBaseActivity extends KMediaActivity {
    /**
     * 碎片集合上一个显示的索引
     */
    protected int currentTabIndex;
    /**
     * 根view
     */
    protected View rootView;
    /**
     * View 管理
     */
    protected ViewHolder mViewHolder;

    /**
     * 判断键盘的高度
     */
    private int keyHeight;

    /**
     * 系统返回键是否起作用
     */
    protected Boolean isCanBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Boolean isShowStatusBar = getShowStatusBar();
        if (!isShowStatusBar) {
            StatusBarUtils.setStatusBar(mActivity, isShowStatusBar);
        }
        Boolean isShowSteep = getShowSteep();
        if (isShowSteep) {
            StatusBarUtils.setTransparent(mActivity);
        }
        Boolean isShowFullScreen = getShowFullScreen();
        if (isShowFullScreen) {
            ScreenUtil.setFullScreen(mActivity);
        }
        int layoutId = getLayoutId();
        mViewHolder = layoutId == 0 ? null : new ViewHolder(getLayoutInflater(), null, layoutId);
        rootView = layoutId == 0 ? null : mViewHolder.getRootView();
        if (rootView != null) {
            setContentView(rootView);
        }
        initView(mViewHolder, rootView);
    }

    /**
     * 获取全屏状态 默认false
     */
    protected Boolean getShowFullScreen() {
        return false;
    }

    /**
     * 获取沉浸式状态 默认false
     */
    protected Boolean getShowSteep() {
        return false;
    }

    /**
     * 获取状态栏显示状态 默认显示
     */
    protected Boolean getShowStatusBar() {
        return true;
    }

    /**
     * 获取布局id
     */

    protected abstract int getLayoutId();

    /**
     * 具体操作
     */
    protected abstract void initView(ViewHolder mViewHolder, View rootView);


    /**
     * 吐司提示
     */
    public void showToast(String string) {
        ToastUtil.showToast(mActivity, string);
    }

    /**
     * 开启软键盘弹出关闭监听
     */
    protected void actionSoftInputChangeListener() {
        //阀值设置为屏幕高度的1/3    
        keyHeight = ScreenUtil.getScreenHeight() * 2 / 3;
        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft,
                                       int oldTop, int oldRight, int oldBottom) {
                Rect rect = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                if (bottom != 0 && oldBottom != 0 && rect.bottom <= keyHeight) {
                    onInputShow();
                } else if (bottom != 0 && oldBottom != 0) {
                    onInputHide();
                }
            }
        });
    }

    /**
     * 软键盘监听--弹出
     */
    protected void onInputShow() {
        LogUtil.i("软键盘弹出");
    }

    /**
     * 软键盘监听--关闭
     */
    protected void onInputHide() {
        LogUtil.i("软键盘关闭");
    }

    /**
     * 开启键盘
     */
    protected void openInput() {
        KeyBordUtil.isShowKeybord(mActivity, true);
    }

    /**
     * 关闭键盘
     */
    protected void closeInput() {
        KeyBordUtil.isShowKeybord(mActivity, false);
    }

    public ViewHolder getViewHolder() {
        return mViewHolder;
    }

    /**
     * 通用findViewById
     */
    protected <T extends View> T findView(int viewId) {
        return (T) findViewById(viewId);
    }


    /**
     * 让字体不随系统字体改变、多语言的时候要换掉，不然切换语言失效
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    public void onBackPressed() {
        finishActivity(mActivity);
    }

    /**
     * 关闭当前activity
     */
    public void finishActivity(Activity activity) {
        NextActivityUtil.finishActivity(activity);
    }

    /**
     * 添加fragment
     *
     * @param fragmentLayoutId 承载布局id
     * @param fragment         碎片实例
     * @param isShow           是否显示
     */
    protected void addFragment(int fragmentLayoutId, Fragment fragment, Boolean isShow) {
        if (fragment != null && fragmentLayoutId != 0) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(fragmentLayoutId, fragment);
            if (isShow) {
                transaction.show(fragment);
            }
            transaction.commit();
        }
    }

    /**
     * 移除fragment
     */
    protected void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    /**
     * 碎片变化
     */
    protected int changeFragments(int fragmentLayoutId, Fragment[] fragments, int index) {
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(fragmentLayoutId, fragments[index]);
            } else {
                trx.show(fragments[index]);
            }
            trx.commit();
        }
        currentTabIndex = index;
        return currentTabIndex;
    }


    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeInput();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getTitleRigthMenu() != 0) {
            getMenuInflater().inflate(getTitleRigthMenu(), menu);
            return true;
        } else {
            return super.onCreateOptionsMenu(menu);
        }
    }

    /**
     * 设置菜单栏右标
     *
     * @return menu文件id
     */
    protected int getTitleRigthMenu() {
        return 0;
    }

    /**
     * 设置菜单栏右标的点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 0) {
            onTitleRightMenuClick(item.getItemId());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 菜单栏右标点击监听
     *
     * @param itemId
     */
    protected void onTitleRightMenuClick(int itemId) {
    }
}
