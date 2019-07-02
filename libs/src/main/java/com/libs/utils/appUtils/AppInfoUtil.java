package com.libs.utils.appUtils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresPermission;
import android.util.Log;

import com.libs.k;
import com.libs.utils.appUtils.activityUtil.ActivitysUtil;
import com.libs.utils.bengUtil.IntentUtil;
import com.libs.utils.fileUtil.FileUtil;
import com.libs.utils.logUtils.LogUtil;
import com.libs.utils.systemUtils.CleanUtils;
import com.libs.utils.systemUtils.ProcessUtil;
import com.libs.utils.systemUtils.ShellUtil;

import java.io.File;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import static com.libs.BuildConfig.DEBUG;
import static com.libs.utils.dataUtil.StringUtil.isSpace;


/**
 * @ author：mo
 * @ data：2016/1/29：15:54
 * @ 功能：App相关工具类
 */
public class AppInfoUtil {

    private AppInfoUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("AppInfoUtil cannot be instantiated");

    }

    /**
     * 获取活动管理器
     */
    public static ActivityManager getActivityManager() {
        return (ActivityManager) k.app().getSystemService(Context.ACTIVITY_SERVICE);
    }

    /**
     * 获取包管理器
     */
    public static PackageManager getPackageManager() {
        return k.app().getPackageManager();
    }

    /**
     * 获取 App 信息
     * <p>AppInfo（名称，图标，包名，版本号，版本 Code，是否系统应用）</p>
     *
     * @return 当前应用的 AppInfo
     */
    public static AppInfo getAppInfo() {
        return getAppInfo(k.app().getPackageName());
    }


    /**
     * 判断 App 是否安装
     *
     * @param action   action
     * @param category category
     * @return
     */
    public static boolean isInstallApp(final String action, final String category) {
        Intent intent = new Intent(action);
        intent.addCategory(category);
        PackageManager pm = k.app().getPackageManager();
        ResolveInfo info = pm.resolveActivity(intent, 0);
        return info != null;
    }

    /**
     * 判断 App 是否安装
     *
     * @param packageName 包名
     * @return
     */
    public static boolean isInstallApp(final String packageName) {
        return !isSpace(packageName) && IntentUtil.getLaunchAppIntent(packageName) != null;
    }

    /**
     * 安装 APK
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void installApp(final String filePath) {
        installApp(FileUtil.getFileByPath(filePath));
    }

    /**
     * 安装 APK 文件
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void installApp(final File file) {
        if (!FileUtil.isFileExists(file)) {
            return;
        }
        k.app().startActivity(IntentUtil.getInstallAppIntent(file));
    }


    /**
     * 静默安装 App
     * <p>非 root 需添加权限
     * {@code <uses-permission android:name="android.permission.INSTALL_PACKAGES" />}</p>
     *
     * @param filePath 文件路径
     * @return {@code true}: 安装成功<br>{@code false}: 安装失败
     */
    public static boolean installAppSilent(final String filePath) {
        File file = FileUtil.getFileByPath(filePath);
        if (!FileUtil.isFileExists(file)) {
            return false;
        }
        boolean isRoot = isDeviceRooted();
        String command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install " + filePath;
        ShellUtil.CommandResult commandResult = ShellUtil.execCmd(command, isRoot);
        if (commandResult.successMsg != null
                && commandResult.successMsg.toLowerCase().contains("success")) {
            return true;
        } else {
            command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib64 pm install " + filePath;
            commandResult = ShellUtil.execCmd(command, isRoot, true);
            return commandResult.successMsg != null
                    && commandResult.successMsg.toLowerCase().contains("success");
        }
    }

    /**
     * 判断微信是否安装
     *
     * @return
     */
    public static boolean isInstallWeixin() {
        final PackageManager packageManager = k.app().getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 判断qq是否安装
     *
     * @return
     */
    public static boolean isInstallQQ() {
        final PackageManager packageManager = k.app().getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 卸载 App
     *
     * @param packageName 包名
     */
    public static void uninstallApp(final String packageName) {
        if (isSpace(packageName)) {
            return;
        }
        k.app().startActivity(IntentUtil.getUninstallAppIntent(packageName));
    }


    /**
     * 静默卸载 App
     * <p>非 root 需添加权限
     * {@code <uses-permission android:name="android.permission.DELETE_PACKAGES" />}</p>
     *
     * @param packageName 包名
     * @param isKeepData  是否保留数据
     * @return {@code true}: 卸载成功<br>{@code false}: 卸载失败
     */
    public static boolean uninstallAppSilent(final String packageName, final boolean isKeepData) {
        if (isSpace(packageName)) {
            return false;
        }
        boolean isRoot = isDeviceRooted();
        String command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm uninstall "
                + (isKeepData ? "-k " : "")
                + packageName;
        ShellUtil.CommandResult commandResult = ShellUtil.execCmd(command, isRoot, true);
        if (commandResult.successMsg != null
                && commandResult.successMsg.toLowerCase().contains("success")) {
            return true;
        } else {
            command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib64 pm uninstall "
                    + (isKeepData ? "-k " : "")
                    + packageName;
            commandResult = ShellUtil.execCmd(command, isRoot, true);
            return commandResult.successMsg != null
                    && commandResult.successMsg.toLowerCase().contains("success");
        }
    }

    /**
     * 判断 App 是否有 root 权限
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppRoot() {
        ShellUtil.CommandResult result = ShellUtil.execCmd("echo root", true);
        if (result.result == 0) {
            return true;
        }
        if (result.errorMsg != null) {
            Log.d("AppUtils", "isAppRoot() called" + result.errorMsg);
        }
        return false;
    }

    /**
     * 关闭 App
     */
    public static void exitApp() {
        ActivitysUtil.AppExit();
    }


    /**
     * 判断 App 是否是 Debug 版本
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppDebug() {
        return isAppDebug(k.app().getPackageName());
    }

    /**
     * 判断 App 是否是 Debug 版本
     *
     * @param packageName 包名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppDebug(final String packageName) {
        if (isSpace(packageName)) {
            return false;
        }
        try {
            PackageManager pm = k.app().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取 App 签名
     *
     * @return App 签名
     */
    public static String getAppSignature() {
        return getAppSignature(k.app().getPackageName());
    }

    /**
     * 获取 App 签名
     *
     * @param packageName 包名
     * @return App 签名
     */
    public static String getAppSignature(final String packageName) {
        if (isSpace(packageName)) {
            return null;
        }
        try {
            PackageManager pm = k.app().getPackageManager();
            @SuppressLint("PackageManagerGetSignatures")
            PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return pi == null ? "" : pi.signatures[0].toCharsString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取应用签名
     *
     * @param packageName 包名
     */
    public static String getAppSignature32(String packageName) {
        try {
            PackageInfo pi = k.app().getPackageManager().getPackageInfo(
                    packageName, PackageManager.GET_SIGNATURES);
            return hexdigest(pi.signatures[0].toByteArray());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将签名字符串转换成需要的32位签名
     *
     * @param paramArrayOfByte 签名byte数组
     * @return 32位签名字符串
     */
    private static String hexdigest(byte[] paramArrayOfByte) {
        final char[] hexDigits = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97,
                98, 99, 100, 101, 102};
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(paramArrayOfByte);
            byte[] arrayOfByte = localMessageDigest.digest();
            char[] arrayOfChar = new char[32];
            for (int i = 0, j = 0; ; i++, j++) {
                if (i >= 16) {
                    return new String(arrayOfChar);
                }
                int k = arrayOfByte[i];
                arrayOfChar[j] = hexDigits[(0xF & k >>> 4)];
                arrayOfChar[++j] = hexDigits[(k & 0xF)];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 判断当前 APP 是否正在前台运行 ,原理是判断 APP 是否处于栈顶, 屏幕是否关闭不会影响结果
     */
    @RequiresPermission(Manifest.permission.GET_TASKS)
    public static boolean isAppOnForeground() {
        return isAppOnForeground(getAppInfo().packageName);
    }

    /**
     * 判断某应用程序(App)是否正在前台运行,原理是判断 APP 是否处于栈顶, 屏幕是否关闭不会影响结果
     *
     * @param packageName 应用程序包名
     * @return true 为是, false 为否
     */
    @RequiresPermission(Manifest.permission.GET_TASKS)
    public static boolean isAppOnForeground(String packageName) {
        ActivityManager activityManager = getActivityManager();
        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            // 应用程序位于堆栈的顶层
            if (tasksInfo.get(0).topActivity.getPackageName().equals(packageName)) {
                return true;
            }
//            for (ActivityManager.RunningAppProcessInfo aInfo : tasksInfo) {
//                if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
//                    return aInfo.processName.equals(k.app().getPackageName());
//                }
//            }
        }
        return false;
    }

    /**
     * 获取栈顶 Activity 的名称
     *
     * @return 栈顶 Activity 的名称
     */
    public static String getTopActivityName(ActivityManager activityManager) {
        ActivityManager mActivityManager = activityManager == null ? getActivityManager() : activityManager;
        if (activityManager != null) {
            return activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        }
        return null;
    }

    /**
     * 判断程序是否在后台运行
     */
    public static boolean isRunBackground() {
        ActivityManager activityManager = (ActivityManager) k.app().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> info = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : info) {
            if (appProcess.processName.equals(k.app().getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    // 表明程序在后台运行
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 判断 App 是否处于前台
     * <p>当不是查看当前 App，且 SDK 大于 21 时，
     * 需添加权限 {@code <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS" />}</p>
     *
     * @param packageName 包名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppForeground(final String packageName) {
        return !isSpace(packageName) && packageName.equals(ProcessUtil.getForegroundProcessName());
    }

    /**
     * @Description 判断app是否在前台还是在后台运行
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                /*
                 * BACKGROUND=400 EMPTY=500 FOREGROUND=100
                 * GONE=1000 PERCEPTIBLE=130 SERVICE=300 ISIBLE=200
                 */
                Log.i(context.getPackageName(), "此appimportace ="
                        + appProcess.importance
                        + ",context.getClass().getName()="
                        + context.getClass().getName());
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    LogUtil.i(context.getPackageName() + "处于后台" + appProcess.processName);
                    return true;
                } else {
                    LogUtil.i(context.getPackageName() + "处于前台" + appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 封装 App 信息的 Bean 类
     */
    public static class AppInfo {
        //app名称
        private String name;
        //app图标
        private Drawable icon;
        //包名
        private String packageName;
        //包路径
        private String packagePath;
        //版本号
        private String versionName;
        //版本码
//        private int versionCode;
        //是否系统应用
        private boolean isSystem;

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(final Drawable icon) {
            this.icon = icon;
        }

        public boolean isSystem() {
            return isSystem;
        }

        public void setSystem(final boolean isSystem) {
            this.isSystem = isSystem;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(final String packageName) {
            this.packageName = packageName;
        }

        public String getPackagePath() {
            return packagePath;
        }

        public void setPackagePath(final String packagePath) {
            this.packagePath = packagePath;
        }

//        public int getVersionCode() {
//            return versionCode;
//        }
//
//        public void setVersionCode(final int versionCode) {
//            this.versionCode = versionCode;
//        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(final String versionName) {
            this.versionName = versionName;
        }

        public AppInfo() {
        }

        @Override
        public String toString() {
            return "pkg name: " + getPackageName() +
                    "\napp name: " + getName() +
                    "\napp path: " + getPackagePath() +
                    "\napp v name: " + getVersionName() +
//                    "\napp v code: " + getVersionCode() +
                    "\nis system: " + isSystem();
        }
    }


    /**
     * 获取 App 信息
     * <p>AppInfo（名称，图标，包名，版本号，版本 Code，是否系统应用）</p>
     *
     * @param packageName 包名
     * @return 当前应用的 AppInfo
     */
    public static AppInfo getAppInfo(final String packageName) {
        try {
            PackageManager pm = k.app().getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(packageName, 0);
            return getBean(pm, packageInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodError e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取版本码，9.0之后更新了方式不能一起拿了，会报so的错，狗日的
     */
    public static long getVersionCode() {
        long versionCode = 0;
        try {
            PackageManager pm = k.app().getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(k.app().getPackageName(), 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                versionCode = packageInfo.getLongVersionCode();
            } else {
                versionCode = packageInfo.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodError e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 得到 AppInfo 的 Bean
     *
     * @param pm 包的管理
     * @param pi 包的信息
     * @return AppInfo 类
     */
    private static AppInfo getBean(final PackageManager pm, final PackageInfo pi) {
        if (pm == null || pi == null) {
            return null;
        }
        ApplicationInfo ai = pi.applicationInfo;
        AppInfo appInfo = new AppInfo();
        appInfo.setName(ai.loadLabel(pm).toString());
        appInfo.setIcon(ai.loadIcon(pm));
        appInfo.setPackageName(pi.packageName);
        appInfo.setPackagePath(ai.sourceDir);
        appInfo.setVersionName(pi.versionName);

        appInfo.setSystem((ApplicationInfo.FLAG_SYSTEM & ai.flags) != 0);
        return appInfo;
    }

    /**
     * 获取所有已安装 App 信息
     * <p>{@link #getBean(PackageManager, PackageInfo)}
     * （名称，图标，包名，包路径，版本号，版本 Code，是否系统应用）</p>
     * <p>依赖上面的 getBean 方法</p>
     *
     * @return 所有已安装的 AppInfo 列表
     */
    public static List<AppInfo> getAppsInfo() {
        List<AppInfo> list = new ArrayList<>();
        PackageManager pm = k.app().getPackageManager();
        // 获取系统中安装的所有软件信息
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
        for (PackageInfo pi : installedPackages) {
            AppInfo ai = getBean(pm, pi);
            if (ai == null) {
                continue;
            }
            list.add(ai);
        }
        return list;
    }

    /**
     * 清除 App 所有数据
     *
     * @param dirPaths 目录路径
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean cleanAppData(final String... dirPaths) {
        File[] dirs = new File[dirPaths.length];
        int i = 0;
        for (String dirPath : dirPaths) {
            dirs[i++] = new File(dirPath);
        }
        return cleanAppData(dirs);
    }

    /**
     * 清除 App 所有数据
     *
     * @param dirs 目录
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean cleanAppData(final File... dirs) {
        boolean isSuccess = CleanUtils.cleanInternalCache();
        isSuccess &= CleanUtils.cleanInternalDbs();
        isSuccess &= CleanUtils.cleanInternalSP();
        isSuccess &= CleanUtils.cleanInternalFiles();
        isSuccess &= CleanUtils.cleanExternalCache();
        for (File dir : dirs) {
            isSuccess &= CleanUtils.cleanCustomCache(dir);
        }
        return isSuccess;
    }

    /**
     * 获取清单里保存的数据
     *
     * @param context
     * @param str//清单里的key
     * @return 串
     */
    public static String getName(Context context, String str) {
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo packageInfo = packageManager.getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            return packageInfo.metaData.getString(str);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检测服务是否运行
     *
     * @param serviceClassName 类名
     * @return 是否运行的状态
     */
    public static boolean isServiceRunning(String serviceClassName) {
        List<ActivityManager.RunningServiceInfo> services = getActivityManager().getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo runningServiceInfo : services) {
            if (runningServiceInfo.service.getClassName().equals(serviceClassName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 停止运行服务
     *
     * @param context   上下文
     * @param className 类名
     * @return 是否执行成功
     */
    public static boolean stopRunningService(Context context, String className) {
        Intent intent_service = null;
        boolean ret = false;
        try {
            intent_service = new Intent(context, Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (intent_service != null) {
            ret = context.stopService(intent_service);
        }
        return ret;
    }


    /**
     * 清理后台进程与服务
     *
     * @return 被清理的数量
     */
    public static int gc() {
        long i = getDeviceUsableMemory();
        int count = 0; // 清理掉的进程数
        ActivityManager am = (ActivityManager) k.app().getSystemService(Context.ACTIVITY_SERVICE);
        // 获取正在运行的service列表
        List<ActivityManager.RunningServiceInfo> serviceList = am.getRunningServices(100);
        if (serviceList != null) {
            for (ActivityManager.RunningServiceInfo service : serviceList) {
                if (service.pid == android.os.Process.myPid()) {
                    continue;
                }
                try {
                    android.os.Process.killProcess(service.pid);
                    count++;
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        }

        // 获取正在运行的进程列表
        List<ActivityManager.RunningAppProcessInfo> processList = am.getRunningAppProcesses();
        if (processList != null) {
            for (ActivityManager.RunningAppProcessInfo process : processList) {
                // 一般数值大于RunningAppProcessInfo.IMPORTANCE_SERVICE的进程都长时间没用或者空进程了
                // 一般数值大于RunningAppProcessInfo.IMPORTANCE_VISIBLE的进程都是非可见进程，也就是在后台运行着
                if (process.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                    // pkgList 得到该进程下运行的包名
                    String[] pkgList = process.pkgList;
                    for (String pkgName : pkgList) {
                        if (DEBUG) {
                            LogUtil.i("======正在杀死包名：" + pkgName);
                        }
                        try {
                            am.killBackgroundProcesses(pkgName);
                            count++;
                        } catch (Exception e) { // 防止意外发生
                            e.getStackTrace();
                        }
                    }
                }
            }
        }
        if (DEBUG) {
            LogUtil.i("清理了" + (getDeviceUsableMemory() - i) + "M内存");
        }
        return count;
    }

    /**
     * 获取设备的可用内存大小
     *
     * @return 当前内存大小
     */
    public static int getDeviceUsableMemory() {
        ActivityManager am = (ActivityManager) k.app().getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        // 返回当前系统的可用内存
        return (int) (mi.availMem / (1024 * 1024));
    }

    private static boolean isDeviceRooted() {
        String su = "su";
        String[] locations = {"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/",
                "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/"};
        for (String location : locations) {
            if (new File(location + su).exists()) {
                return true;
            }
        }
        return false;
    }
}
