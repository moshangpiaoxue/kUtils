package com.kutils;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.libs.modle.viewHolder.ViewHolder;
import com.libs.ui.activity.KBaseActivity;
import com.libs.utils.bengUtil.NextActivityUtil;
import com.libs.utils.task.threadPool.ThreadPoolProxyFactory;


/**
 * @ author：mo
 * @ data：2019/6/14:17:16
 * @ 功能：闪屏页
 */
public class SplashActivity extends KBaseActivity {
    private ImageView iv_splash;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView(ViewHolder mViewHolder, View rootView) {
        iv_splash = findViewById(R.id.iv_splash);
        Glide.with(mActivity).
                load("http://pic37.nipic.com/20140113/8800276_184927469000_2.png").
                diskCacheStrategy(DiskCacheStrategy.RESULT).
                thumbnail(0.5f).
                priority(Priority.HIGH).
                placeholder(R.mipmap.aa).
                error(R.mipmap.aa).
                fallback(R.mipmap.aa).
                into(iv_splash);
        ThreadPoolProxyFactory.getSingleThreadPool().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                NextActivityUtil.toNextActivity(mActivity, MainActivity.class, true);
            }
        });
    }
}
