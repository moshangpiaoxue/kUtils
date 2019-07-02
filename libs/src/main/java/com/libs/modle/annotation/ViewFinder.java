package com.libs.modle.annotation;

import android.app.Activity;
import android.view.View;

/**
 * @ author：mo
 * @ data：2019/2/25:14:09
 * @ 功能：ViewFinder
 */
public class ViewFinder {
    private View mView;
    private Activity mActivity;

    public ViewFinder(View mView) {
        this.mView = mView;
    }

    public ViewFinder(Activity mActivity) {
        this.mActivity = mActivity;
    }

    /**
     * 通过id找到view
     */
    public View findViewById(int id) {
        if (mView != null) {
            return mView.findViewById(id);
        }
        if (mActivity != null) {
            return mActivity.findViewById(id);
        }
        return null;
    }

    /**
     * 通过id和父布局id找view
     */
    public View findViewById(int id, int pid) {
        View pView = null;
        if (pid > 0) {
            pView = this.findViewById(pid);
        }

        View view = null;
        if (pView != null) {
            view = pView.findViewById(id);
        } else {
            view = this.findViewById(id);
        }
        return view;
    }
}
