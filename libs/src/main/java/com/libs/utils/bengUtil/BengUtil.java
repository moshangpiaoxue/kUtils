package com.libs.utils.bengUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.libs.R;

import java.io.Serializable;


/**
 * @ author：mo
 * @ data：2019/5/29:15:45
 * @ 功能：
 */
public class BengUtil {
    /**
     * 在跳入界面里拿带过来的数据
     * 注1：可拿一般的数据类型和实体类，实体类一定要使用Serializable或Parcelable，进行序列化处理
     * 注2：只能拿一种数据，跳的时候就只能带一个附加参数，拿的时候返回的是object，需要自己转换，
     */
    public static Object getData(Activity activity) {
        if (activity == null) {
            return null;
        }
        Intent intent = activity.getIntent();
        if (intent == null) {
            return null;
        }
        return (intent.getSerializableExtra("extra") == null ? intent.getParcelableExtra("extra") : intent.getSerializableExtra("extra"));
//        //空代表实体类
//        if (obj == null) {
//            return (intent.getSerializableExtra("extra") == null ? intent.getParcelableExtra("extra") : intent.getSerializableExtra("extra"));
//        } else if (obj instanceof String) {
//            String extra = intent.getStringExtra("extra");
//            return StringUtil.isEmpty(extra) ? obj : extra;
//        } else if (obj instanceof Integer) {
//            return intent.getIntExtra("extra", (Integer) obj);
//        } else if (obj instanceof Boolean) {
//            return intent.getBooleanExtra("extra", (Boolean) obj);
//        }
//        return null;
    }

    /**
     * 在跳入界面里拿带过来的数据
     */
    public static Bundle getExtras(Activity activity) {
        if (activity == null) {
            return null;
        }
        Intent intent = activity.getIntent();
        if (intent == null) {
            return null;
        }
        return intent.getExtras();
    }

    /**
     * 跳出界面（建造者）
     *
     * @param fromAct  起点activity
     * @param toAct    终点activity
     * @param isFinish 是否杀死起点activity
     * @return Builder 建造者 可追加配置
     */
    public static Builder getBuilder(Activity fromAct, Class<?> toAct, Boolean isFinish) {
        return new Builder(fromAct, toAct, isFinish);
    }


    public static class Builder {
        /**
         * 当前活动
         */
        private Activity fromAct;
        /**
         * 目标活动
         */
        private Class<?> toAct;
        /**
         * 是否杀死当前活动
         */
        private Boolean isFinish;
        /**
         * 意图
         */
        private Intent intent;
        /**
         * 是否开启跳转动画，默认否
         */
        private Boolean isAnimation = false;
        /**
         * 跳入活动的进入动画
         */
        private int enterAnim = R.anim.kpush_left_in;
        /**
         * 跳出活动的离开动画
         */
        private int exitAnim = R.anim.kpush_left_out;
        /**
         * 跳转带的数据
         */
        private Bundle bundle;
        /**
         * 跳下个界面的启动模式
         */
        private int flag = -1;
        /**
         * 是否需要返回数据（onActivityResult）
         */
        private boolean isForResult = false;
        /**
         * 默认返回的结果码
         */
        private int resultCode = 1;

        public Builder(Activity fromAct, Class<?> toAct, Boolean isFinish) {
            this.fromAct = fromAct;
            this.toAct = toAct;
            this.isFinish = isFinish;
            intent = new Intent(fromAct, toAct);
            bundle = new Bundle();
        }

        /**
         * 设置数据返回
         *
         * @param forResult  跳出后是否还通过onActivityResult(int requestCode, int resultCode, Intent data)接收返回数据
         * @param resultCode 请求码，好像是不能小于0
         *                   数据返回后，通过onActivityResult(int requestCode, int resultCode, Intent data)接收
         *                   ，resultCode对应的是方法里的requestCode参数，这两个值相等，说明onActivityResult方法里的数据是我想要的
         * @return this
         */
        public Builder setForResult(boolean forResult, int resultCode) {
            this.isForResult = forResult;
            this.resultCode = resultCode < 0 ? 1 : resultCode;
            return this;
        }


        /**
         * 设置被跳入activity的启动模式(启动模式这个说法可能不太准确，意会吧。。。)
         *
         * @param flag :
         *             FLAG_ACTIVITY_NEW_TASK=如果已经存在了一个与新activity有着同样affinity的任务，则activity会载入那个任务之中。如果没有，则启用新任务。
         *             FLAG_ACTIVITY_CLEAR_TOP=跳转到的activity若已在栈中存在，则将其上的activity都销掉。
         *             FLAG_ACTIVITY_SINGLE_TOP=目标activity已在栈顶则跳转过去，不在栈顶则在栈顶新建activity。
         *             FLAG_ACTIVITY_NO_HISTORY=跳转到的activity不压在栈中
         * @return this
         */
        public Builder setFlag(int flag) {
            this.flag = flag;
            return this;
        }

        /**
         * 设置携带的数据（默认key为 extra,思路是只能传一个，多个的话使用setBundle(Bundle bundle) 方法）
         */
        public Builder setObject(Object obj) {
            if (obj instanceof Parcelable) {
                bundle.putParcelable("extra", (Parcelable) obj);
            }
            if (obj instanceof Serializable) {
                bundle.putSerializable("extra", (Serializable) obj);
            }
            return this;
        }

        /**
         * 设置携带数据
         */
        public Builder setBundle(Bundle bundle) {
            this.bundle = bundle;
            return this;
        }

        /**
         * 设置是否有跳转动画
         */
        public Builder setCanAnimation(Boolean animation) {
            this.isAnimation = animation;
            return this;
        }

        /**
         * 设置跳入活动的进入动画\跳出活动的离开动画
         */
        public Builder setAnimation(int enterAnim, int exitAnim) {
            this.enterAnim = enterAnim;
            this.exitAnim = exitAnim;
            return this;
        }


        /**
         * 起跳
         */
        public void action() {
            if (intent == null || bundle == null) {
                return;
            }
            intent.putExtras(bundle);
            if (flag != -1) {
                intent.addFlags(flag);
            }
            if (isForResult) {
                fromAct.startActivityForResult(intent, resultCode);
            } else {
                fromAct.startActivity(intent);
            }

            if (!isForResult && isFinish) {
                fromAct.finish();
            }
            if (isAnimation) {
                fromAct.overridePendingTransition(enterAnim, exitAnim);
            }

        }
    }
}
