package com.libs.utils.bengUtil;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import com.libs.k;
import com.libs.utils.appUtils.AppInfoUtil;
import com.libs.utils.dataUtil.StringUtil;
import com.libs.utils.logUtils.LogUtil;
import com.libs.utils.tipsUtil.ToastUtil;

import java.util.ArrayList;
import java.util.List;



/**
 * @ author：mo
 * @ data：2018/10/12
 * @ 功能：跳应用市场工具类
 */
public class ToMarketUtil {
    /**
     * 检查已安装的应用商店
     * 但是小米商店目前检测不出，先定义为bug
     *
     * @return 返回包名列表
     */
    public static List<String> checkMarket() {
        List<String> packageNames = new ArrayList<>();
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.APP_MARKET");
        PackageManager pm = k.app().getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
        int size = infos.size();
        for (int i = 0; i < size; i++) {
            ActivityInfo activityInfo = infos.get(i).activityInfo;
            String packageName = activityInfo.packageName;
            LogUtil.i("packageName : " + packageName);
            packageNames.add(packageName);
        }
        return packageNames;
    }

    /**
     * 跳转应用商店
     *
     * @param marketPkg 应用商店包名  null==跳默认市场，如果装有多个市场，会弹列表选择，但是 实测装应用宝和华为市场时，默认跳转了应用宝
     *                  com.android.vending	Google Play
     *                  com.tencent.android.qqdownloader	应用宝
     *                  com.qihoo.appstore	360手机助手
     *                  com.baidu.appsearch	百度手机助
     *                  com.xiaomi.market	小米应用商店
     *                  com.wandoujia.phoenix2	豌豆荚
     *                  com.huawei.appmarket	华为应用市场
     *                  com.taobao.appcenter	淘宝手机助手
     *                  com.hiapk.marketpho	安卓市场
     *                  cn.goapk.market	安智市场
     * @return
     */
    public static void toMarket(String marketPkg) {
        Uri uri = Uri.parse("market://details?id=" + AppInfoUtil.getAppInfo().getPackageName());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 如果没给市场的包名，则系统会弹出市场的列表让你进行选择。
        if (!StringUtil.isEmpty(marketPkg)) {
            intent.setPackage(marketPkg);
        }
        try {
            k.app().startActivity(intent);
        } catch (Exception ex) {
            LogUtil.i("没有安装应用市场或参数错了");
            ToastUtil.showToast("提示没有安装应用市场");
            ex.printStackTrace();
        }
    }

    /**
     * 跳转三星应用商店
     *
     * @return
     */
    public static void goToSamsungMarket() {
        Uri uri = Uri.parse("http://www.samsungapps.com/appquery/appDetail.as?appId=" + AppInfoUtil.getAppInfo().getPackageName());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.sec.android.app.samsungapps");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            k.app().startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 直接跳转至应用宝
     *
     * @return {@code true} 跳转成功 <br> {@code false} 跳转失败
     */
    public static void toQQDownload() {
        toMarket("com.tencent.android.qqdownloader");
    }

    /**
     * 直接跳转至360手机助手
     *
     * @return {@code true} 跳转成功 <br> {@code false} 跳转失败
     */
    public static void to360Download() {

        toMarket("com.qihoo.appstore");
    }

    /**
     * 直接跳转至豌豆荚
     *
     * @return {@code true} 跳转成功 <br> {@code false} 跳转失败
     */
    public static void toWandoujia() {
        toMarket("com.wandoujia.phoenix2");
    }

    /**
     * 直接跳转至小米应用商店
     *
     * @return {@code true} 跳转成功 <br> {@code false} 跳转失败
     */
    public static void toXiaoMi() {
        toMarket("com.xiaomi.market");
    }

    /**
     * 直接跳转至魅族应用商店
     *
     * @return {@code true} 跳转成功 <br> {@code false} 跳转失败
     */
    public static void toMeizu() {
        toMarket("com.meizu.mstore");
    }
}
