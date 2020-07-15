package com.libs.utils.tipsUtil;

import android.app.Activity;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hjq.toast.ToastUtils;
import com.libs.R;
import com.libs.k;
import com.libs.utils.viewUtil.ViewUtil;


/**
 * @ author：mo
 * @ data：2018/10/12
 * @ 功能：
 * https://github.com/Blincheng/EToast2
 * https://github.com/getActivity/ToastUtils（在华为有些机型上不弹 比如Nova4）
 */
public class ToastUtil {

    public static void showToast(final String msg) {
        showToast(msg, 1000);

    }

    public static void showToast(final String msg, int time) {
        //        ToastUtils.show(msg);
        //        //如果是主线程，直接弹出toast
        //        if ("main".equals(Thread.currentThread().getName())) {
        //            Toast.makeText(k.app(), msg, time).show();
        //        }
        try {
            //            Toast.makeText(k.app(), msg, time).show();
            showToastIos(msg);
        } catch (Exception e) {
            //解决在子线程中调用Toast的异常情况处理
            Looper.prepare();
            //            Toast.makeText(k.app(), msg, time).show();
            showToastIos(msg);
            Looper.loop();
        }

    }

    public static void showToast(final Activity ctx, final String msg) {
        if (ctx != null) {
            //如果是主线程，直接弹出toast
            if ("main".equals(Thread.currentThread().getName())) {
                //                Toast.makeText(ctx, msg, 1000).show();
                showToastIos(msg);
            } else {
                //如果不是主线程，则调用context中 runOnUIThread方法弹出toast
                ctx.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //                        Toast.makeText(ctx, msg, 1000).show();
                        showToastIos(msg);
                    }
                });
            }
        }
    }

    public static void showToastIos(String msg) {
        ToastUtils.setGravity(Gravity.BOTTOM,0,150);
        ToastUtils.show(msg);
        //        Toast toast = Toast.makeText(k.app(), msg, Toast.LENGTH_SHORT);
        //        LinearLayout layout = (LinearLayout) toast.getView();
        //        layout.setBackgroundResource(R.drawable.k_bg_ios_toast2);
        //        TextView tv = (TextView) layout.getChildAt(0);
        //        tv.setTextColor(Color.WHITE);
        //        tv.setTextSize(14);
        //        toast.show();

//        Toast toast = new Toast(k.app());
//        View layout = ViewUtil.getView(k.app(), R.layout.tost_ios, null);
//        TextView tv_ios_toast = layout.findViewById(R.id.tv_ios_toast);
//        tv_ios_toast.setText(msg);
////        layout.setAlpha(0.7f);
//        toast.setView(layout);
//        toast.setDuration(android.widget.Toast.LENGTH_SHORT);
////        toast.setGravity(Gravity.TOP, 0, 150);
//        toast.show();
    }


}
