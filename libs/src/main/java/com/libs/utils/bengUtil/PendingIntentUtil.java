package com.libs.utils.bengUtil;

import android.app.PendingIntent;
import android.content.Intent;

import com.libs.k;


/**
 * @ author：mo
 * @ data：2019/1/7
 * @ 功能：PendingIntent工具类
 * @ 使用范围：通知、SmsManager,AlarmManager等
 * PendingIntent是一个特殊的Intent，主要区别是intent是立马执行，PendingIntent是待确定的Intent。
 * PendingIntent的操作实际上是传入的intent的操作
 */
public class PendingIntentUtil {
    /**
     * 获取针对activity的待确定意图
     *
     * @param intent 具体意图
     * @return
     */
    public static PendingIntent getPendingIntentForActivity(Intent intent) {
        /**
         * 参数依次为：
         * 环境：context，可使用全局
         * 请求码：requestCode 一般使用0
         * intent：具体意图，跳转，带数据之类的
         * flags：标识：常用PendingIntent.FLAG_UPDATE_CURRENT
         *               PendingIntent.FLAG_CANCEL_CURRENT==如果构建的PendingIntent已经存在，则取消前一个，重新构建一个。
         *               PendingIntent.FLAG_NO_CREATE==如果前一个PendingIntent已经不存在了，将不再构建它。
         *               PendingIntent.FLAG_ONE_SHOT==只作用一次,即：它触发后会自动销毁，再次触发会报SendIntentException异常,表明这里构建的PendingIntent只能使用一次。
         *               PendingIntent.FLAG_UPDATE_CURRENT==如果构建的PendingIntent已经存在，则替换它，常用.例如更新Intent中的Extras。

         */
        return PendingIntent.getActivity(k.app(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * 获取针对多个activity的待确定意图
     *
     * @param intent 意图数组
     * @return
     */
    public static PendingIntent getPendingIntentForActivities(Intent[] intent) {
        return PendingIntent.getActivities(k.app(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * 获取针对广播的待确定意图
     *
     * @param intent
     * @return
     */
    public static PendingIntent getPendingIntentForBroadcast(Intent intent) {
        return PendingIntent.getBroadcast(k.app(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * 获取针对服务的待确定意图
     *
     * @param intent
     * @return
     */
    public static PendingIntent getService(Intent intent) {
        return PendingIntent.getService(k.app(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
