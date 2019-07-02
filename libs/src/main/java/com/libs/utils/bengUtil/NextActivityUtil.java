package com.libs.utils.bengUtil;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import com.libs.k;

import java.io.Serializable;



/**
 * description: 跳界面工具类
 * autour: mo
 * date: 2017/8/2 0002 18:38
 */
public class NextActivityUtil {

    /**
     * 获取Activity所带Object数据
     * List<Icons> list= (List<Icons>) NextActivityUtil.getDate(this);
     * 注意：bean类必须序列化，Serializable或Parcelable，拿数据的时候log会打印报错，
     * 是因为我拿的时候不知道你是用哪种方式序列化的只能先用Serializable拿一下，然后判空，
     * @param activity 载体
     * @return
     */
    public static Object getDateBean(Activity activity) {
//        return activity.getIntent() == null ? null : activity.getIntent().getSerializableExtra("extra");
        Intent intent = activity.getIntent();
        return intent==null?null:(intent.getSerializableExtra("extra")==null?intent.getParcelableExtra("extra"):intent.getSerializableExtra("extra"));

    }

    /**
     * 获取activity所带Int数据
     *
     * @param activity 载体
     * @return
     */
    public static int getDataInt(Activity activity) {
        return activity.getIntent() == null ? -1 : activity.getIntent().getIntExtra("extra", 0);
    }

    /**
     * 获取activity所带String数据
     *
     * @param activity 载体
     * @return
     */
    public static String getDataString(Activity activity) {
        return activity.getIntent() == null ? "" : activity.getIntent().getStringExtra("extra");
    }

    /**
     * 跳界面带数据 bundle
     *
     * @param fromAct  当前界面
     * @param toAct    要去的界面
     * @param bundle   要带的数据
     * @param isFinish 是否结束当前界面
     */
    public static void toNextActivity(Activity fromAct, Class<?> toAct, Bundle bundle, final Boolean isFinish) {
        if (bundle == null) {
            return;
        }
        Intent intent = new Intent(fromAct, toAct);
        intent.putExtras(bundle);
        toNextActivity(fromAct, intent, isFinish);
    }

    /**
     * 跳界面   intent
     *
     * @param fromAct
     * @param intent   爱带啥带啥
     * @param isFinish
     */
    public static void toNextActivity(Activity fromAct, Intent intent, final Boolean isFinish) {
        if (intent == null) {
            return;
        }
        fromAct.startActivity(intent);
        if (isFinish) {
            fromAct.finish();
        }
        startActivityAni(fromAct);
    }

    /**
     * 开启去往下一个界面动画
     *
     * @param fromAct
     */
    public static void startActivityAni(Activity fromAct) {
//        fromAct.overridePendingTransition(R.anim.kpush_left_in, R.anim.kpush_left_out);
    }

    /**
     * 跳界面不带数据
     *
     * @param fromAct  当前界面
     * @param toAct    要去的界面
     * @param isFinish 是否结束当前界面
     */
    public static void toNextActivity(Activity fromAct, Class<?> toAct, final Boolean isFinish) {
        Intent intent = new Intent(fromAct, toAct);
        toNextActivity(fromAct, intent, isFinish);
    }
    /**
     *跳界面不带数据(默认不杀死当前界面)
     * @param fromAct   当前界面
     * @param toAct 要去的界面
     */
    public static void toNextActivity(Activity fromAct, Class<?> toAct) {
        Intent intent = new Intent(fromAct, toAct);
        toNextActivity(fromAct, intent, false);
    }
    /**
     * 跳界面带字符串
     *
     * @param fromAct  当前界面
     * @param toAct    要去的界面
     * @param str      带的参数
     * @param isFinish 是否结束当前界面
     */
    public static void toNextActivity(Activity fromAct, Class<?> toAct, String str, Boolean isFinish) {
        Intent intent = new Intent(fromAct, toAct);
        intent.putExtra("extra", str);
        toNextActivity(fromAct, intent, isFinish);
    }

    /**
     * 跳界面带实体类
     *
     * @param fromAct  当前界面
     * @param toAct    要去的界面
     * @param obj      带的参数
     * @param isFinish 是否结束当前界面
     */
    public static void toNextActivity(Activity fromAct, Class<?> toAct, Object obj, Boolean isFinish) {
        Intent intent = new Intent(fromAct, toAct);
        Bundle bundle = new Bundle();
        if (obj instanceof Parcelable) {
            bundle.putParcelable("extra", (Parcelable) obj);
        }
        if (obj instanceof Serializable) {
            bundle.putSerializable("extra", (Serializable) obj);
        }
        intent.putExtras(bundle);
        toNextActivity(fromAct, intent, isFinish);
    }

    /**
     * 跳界面，要求下个界面返回数据
     *
     * @param fromAct
     * @param toAct
     * @param flag
     */
    public static void toNextActivityResult(Activity fromAct, Class<?> toAct, int flag) {
        fromAct.startActivityForResult(new Intent(fromAct, toAct), flag);
    }

    /**
     * 跳界面，返回数据到下个界面的onActivityResult方法里，
     *
     * @param fromAct
     * @param toAct
     * @param obj
     * @param flag
     * @param isFinish
     */
    public static void toNextActivitySetResault(Activity fromAct, Class<?> toAct, Object obj, int flag, Boolean isFinish) {
        Intent intent = new Intent(fromAct, toAct);
        Bundle bundle = new Bundle();
        if (obj instanceof Parcelable) {
            bundle.putParcelable("extra", (Parcelable) obj);
        }
        if (obj instanceof Serializable) {
            bundle.putSerializable("extra", (Serializable) obj);
        }
        intent.putExtras(bundle);
        fromAct.setResult(flag, intent);
        if (isFinish) {
            fromAct.finish();
        }
    }

    public static void toNextActivitySetResault(Activity fromAct, Class<?> toAct, String str, int flag, Boolean isFinish) {
        Intent intent = new Intent(fromAct, toAct);
        intent.putExtra("extra", str);
        fromAct.setResult(flag, intent);
        if (isFinish) {
            fromAct.finish();
        }
    }

    /**
     * 跳界面带int
     *
     * @param fromAct  当前界面
     * @param toAct    要去的界面
     * @param in       带的参数
     * @param isFinish 是否结束当前界面
     */
    public static void toNextActivityInt(Activity fromAct, Class<?> toAct, int in, Boolean isFinish) {
        Intent intent = new Intent(fromAct, toAct);
        Bundle bundle = new Bundle();
        bundle.putInt("extra", in);
        intent.putExtras(bundle);
        toNextActivity(fromAct, intent, isFinish);
    }

    /**
     * 跳界面带int
     *
     * @param fromAct  当前界面
     * @param toAct    要去的界面
     * @param k        key值
     * @param v        带的参数
     * @param isFinish 是否结束当前界面
     */
    public static void toNextActivity(Activity fromAct, Class<?> toAct, String k, int v, Boolean isFinish) {
        Intent intent = new Intent(fromAct, toAct);
        intent.putExtra(k, v);
        toNextActivity(fromAct, intent, isFinish);
    }

    /**
     * 跳界面带实体类，带int标识
     *
     * @param fromAct  当前界面
     * @param toAct    要去的界面
     * @param obj      带的实体类参数
     * @param flag     带的int参数
     * @param isFinish 是否结束当前界面
     */
    public static void toNextActivity(Activity fromAct, Class<?> toAct, Object obj, int flag, Boolean isFinish) {
        Intent intent = new Intent(fromAct, toAct);
        Bundle bundle = new Bundle();
        if (obj instanceof Parcelable) {
            bundle.putParcelable("extra", (Parcelable) obj);
        }
        if (obj instanceof Serializable) {
            bundle.putSerializable("extra", (Serializable) obj);
        }
        intent.putExtras(bundle);
        intent.putExtra("flag", flag);
        toNextActivity(fromAct, intent, isFinish);
    }

    /**
     * 跳界面带Boolean
     *
     * @param fromAct  当前界面
     * @param toAct    要去的界面
     * @param b        带的参数
     * @param isFinish 是否结束当前界面
     */
    public static void toNextActivityInt(Activity fromAct, Class<?> toAct, Boolean b, Boolean isFinish) {
        Intent intent = new Intent(fromAct, toAct);
        Bundle bundle = new Bundle();
        bundle.putBoolean("extra", b);
        intent.putExtras(bundle);
        toNextActivity(fromAct, intent, isFinish);
    }

    /**
     * 跳界面带Boolean
     *
     * @param fromAct  当前界面
     * @param toAct    要去的界面
     * @param k        key值
     * @param v        带的参数
     * @param isFinish 是否结束当前界面
     */
    public static void toNextActivity(Activity fromAct, Class<?> toAct, String k, Boolean v, Boolean isFinish) {
        Intent intent = new Intent(fromAct, toAct);
        intent.putExtra(k, v);
        toNextActivity(fromAct, intent, isFinish);
    }

    /**
     * 跳界面-转场动画
     *
     * @param mActivity 当前activity
     * @param mIntent   意图
     * @param mView     一般是被点击view，就是动画的起点view
     * @param tag       联动标识
     */
    public static void toNextActivity(Activity mActivity, Intent mIntent, View mView, String tag) {
        // 判断版本号，转场动画是在5.0以后添加的，所以它只在21版本以上起作用
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //设置转场动画，makeSceneTransitionAnimation为固定动画，实现放大效果，
            // 参数依次为：mActivity=当前activity，mView=当前跳出view，tag=与下一个activity联系的string串
            //tag的使用，与下一个界面产生联系
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mActivity, mView, tag);
            mActivity.startActivity(mIntent, options.toBundle());
        } else {
            NextActivityUtil.toNextActivity(mActivity, mIntent, false);
        }
    }

    /**
     * 转场到动画返回
     *
     * @param mActivity
     */
    public static void finishAfterTransition1(Activity mActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mActivity.finishAfterTransition();
        } else {
            NextActivityUtil.finishActivity(mActivity);
        }

    }

    /**
     * 关闭当前界面
     *
     * @param fromAct 当前界面
     */
    public static void finishActivity(Activity fromAct) {
        fromAct.finish();
//        fromAct.overridePendingTransition(R.anim.kpush_right_in, R.anim.kpush_right_out);
    }

    /**
     * 重启当前应用
     */
    public static void restart() {
        Intent intent = k.app().getPackageManager().getLaunchIntentForPackage(k.app().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        k.app().startActivity(intent);
    }
}
