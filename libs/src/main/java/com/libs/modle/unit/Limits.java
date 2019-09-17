package com.libs.modle.unit;

import android.content.pm.ActivityInfo;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * @ author：mo
 * @ data：2019/1/31:17:53
 * @ 功能：限制类
 */
public class Limits {
    /**
     * 手机屏幕方向限制
     */
    @IntDef({
            //默认值，系统根据方向感应自动选择屏幕方向
            ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED,
            //正向横屏，显示的宽比高长（锁死为横屏方向，不再让方向感应起作用）
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE,
            //正向竖屏，显示的高比宽长（锁死为竖屏方向，不再让方向感应起作用）
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT,
            //用户当前首选的方向
            ActivityInfo.SCREEN_ORIENTATION_USER,
            //它跟Activity堆栈中的下面一个Activity的方向一致
            ActivityInfo.SCREEN_ORIENTATION_BEHIND,
            //由设备的方向传感器决定，如果用户旋转设备，这屏幕就会横竖屏切换（这里要注意了：这个属性有些强悍。大家知道安卓手机上都有一个“屏幕旋转”按钮，有的也叫“锁定屏幕”随便什么名字不管了，这个设置里面的开关和Activity有密切关系。关闭它之后Activity界面就不能响应方向传感器了，打开它才会恢复正常，挺合理的一个功能，让决定权放在用户手中。可一旦你设置了这个属性，无论用户怎么设置自己的手机上的“屏幕旋转”按钮，打开也好，关闭也好，Activity界面都会响应方向传感器的，会随着用户手持手机的方向自动变化，这就让用户有点奇怪。）
            ActivityInfo.SCREEN_ORIENTATION_SENSOR,
            //忽略物理方向传感器，这样就不会随着用户旋转设备而横竖屏切换了（这里有个坑：如果用户横屏拿着手机进行播放，界面也是横屏的，一旦设置了这个属性之后，手机界面会先变换到竖屏，然后才会锁死方向传感器）
            ActivityInfo.SCREEN_ORIENTATION_NOSENSOR,
            //横屏，和上面它“爸爸”SENSOR一样强悍，无视用户手机设置的“屏幕旋转”按钮开关，直接根据方向传感器来切换正反向横屏，但是不会切换到竖屏
            ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE,
            //竖屏，和上面它“爸爸”SENSOR一样强悍，无视用户手机设置的“屏幕旋转”按钮开关，直接根据方向传感器来切换正反向竖屏，不会切换到横屏
            ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT,
            //反向横屏，横屏分正向横屏（靠左手方向横屏）和反向横屏（靠右手方向横屏）
            ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE,
            //反向竖屏，就是和正常竖着拿手机相反，竖着掉了个个
            ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT,
            //方向由 4 种方向中任一方向的设备方向传感器决定。这与 "sensor" 类似，不同的是它允许所有 4 种可能的屏幕方向，无论设备正常情况下采用什么方向（例如，一些设备正常情况下不使用反向纵向或反向横向，但它支持这些方向）。
            ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ScreenOrientation {
    }

    /**
     * 文件大小的单位
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({FileSize.B, FileSize.KB, FileSize.MB, FileSize.GB})
    public @interface FileSizeDef {
    }

    /**
     * 时间转换单位倍数
     */
    @IntDef({TimeUnit.MSEC, TimeUnit.SEC, TimeUnit.MIN, TimeUnit.HOUR, TimeUnit.DAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface UnitMS {
    }

}
