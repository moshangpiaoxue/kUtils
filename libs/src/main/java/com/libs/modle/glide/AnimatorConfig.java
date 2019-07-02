package com.libs.modle.glide;//package towatt.after_sale_android.lib.mo.modle.imageLoader.glide;
//
//
//import android.animation.ObjectAnimator;
//import android.view.View;
//
//import com.bumptech.glide.request.animation.ViewPropertyAnimation;
//
///**
// * description: 动画配置
// * autour: mo
// * date: 2017/9/4 0004 14:13
// */
//public class AnimatorConfig {
//    /**
//     * 渐现动画
//     *
//     * @return
//     */
//    public static ViewPropertyAnimation.Animator getFadeAnmimator() {
//        return new ViewPropertyAnimation.Animator() {
//            @Override
//            public void animate(View view) {
//                // if it's a custom view class, cast it here
//                // then find subviews and do the animations
//                // here, we just use the entire view for the fade animation
//                view.setAlpha(0f);
//
//                ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
//                fadeAnim.setDuration(1500);
//                fadeAnim.start();
//            }
//        };
//    }
//}
