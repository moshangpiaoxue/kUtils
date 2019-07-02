package com.libs.utils.appUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.libs.k;

import java.util.ArrayList;
import java.util.List;



/**
 * @ author：mo
 * @ data：2019/1/11：14:02
 * @ 功能：权限工具类
 */
public enum PermissionUtil {
    /**
     * 枚举单例
     */
    INSTANCE;
    //权限请求码
    private int mRequestCode = 0x00099;
    private int mRequestCode2 = 0x00098;
    /**
     * 当没有权限的时候，是否显示跳系统设置弹窗
     */
    public boolean showSystemSetting = true;
    /**
     * 没有权限时的提示对话框
     */
    AlertDialog mPermissionDialog;
    /**
     * 请求结果 监听
     */
    private KPermissionsListener permissionsListener;
    List<String> mPermissionList;

    /**
     * 请求权限
     *
     * @param activity            载体
     * @param permissions         权限数组
     * @param requestCode         请求码
     * @param showSystemSetting   被拒后是否显示dialog
     * @param permissionsListener 请求结果监听
     */
    public void requestPermission(Activity activity, String[] permissions, int requestCode,
                                  boolean showSystemSetting,
                                  @NonNull KPermissionsListener permissionsListener) {
        this.mRequestCode = requestCode;
        this.showSystemSetting = showSystemSetting;
        this.permissionsListener = permissionsListener;
        //6.0以下不用动态申请用动态权限
        if (Build.VERSION.SDK_INT < 23) {
            permissionsListener.hasPermissions(true);
            return;
        }

        //创建一个mPermissionList，逐个判断哪些权限未授予，未授予的权限存储到mPerrrmissionList中

        mPermissionList = new ArrayList<>();
        //逐个判断你要的权限是否已经通过
        for (int i = 0; i < permissions.length; i++) {
            if (checkSelfPermission(permissions[i])) {
                //添加还未授予的权限
                mPermissionList.add(permissions[i]);
            }
        }
        //申请权限
        //有权限没有通过，需要申请
        if (mPermissionList.size() > 0) {
            ActivityCompat.requestPermissions(activity, permissions, mRequestCode);
        } else {
            //说明权限都已经通过
            permissionsListener.hasPermissions(true);
        }


    }


    /**
     * 申请权限后处理
     * 在 activity的onRequestPermissionsResult（）方法里，只要请求了就必须调用
     *
     * @param activity     载体
     * @param requestCode  请求码
     * @param permissions  请求的权限数组
     * @param grantResults 请求权限结果 数组的数据0表示允许权限，-1表示我们点击了禁止权限
     */
    public void onRequestPermissionsResult(Activity activity, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //默认有权限没有通过
        if (mRequestCode == requestCode) {
            //如果有权限没有被允许
            if (!verifyPermissions(grantResults)) {
                if (showSystemSetting) {
                    //跳转到系统设置权限页面，或者直接关闭页面，不让他继续访问
                    showSystemPermissionsSettingDialog(activity);
                } else {
                    permissionsListener.hasPermissions(false);
                }
            } else {
                //全部权限通过，可以进行下一步操作。。。
                permissionsListener.hasPermissions(true);
            }
        }
    }

    public void onActivityResult(Activity activity, int requestCode) {
        String[] strings = new String[mPermissionList.size()];
        requestPermission(activity, mPermissionList.toArray(strings), requestCode, showSystemSetting, permissionsListener);
    }

    /**
     * 检测是否有这个权限
     *
     * @param permission
     * @return
     */
    public boolean checkSelfPermission(@NonNull String permission) {
        return ContextCompat.checkSelfPermission(k.app(), permission) != PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 确认所有的权限是否都已授权
     * 此方法用于 onRequestPermissionsResult方法的第三个参数验证
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    /**
     * 是否同意过授权此权限 4.4以下默认为true @param permission @return
     */
    public static boolean isAgreePermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ? true
                : ContextCompat.checkSelfPermission(k.app(), permission)
                == PackageManager.PERMISSION_GRANTED;
    }
    /**
     * 是否同意过授权全部权限
     * 4.4以下默认为true
     */
    public static boolean isAgreePermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT < 24) {
            return true;
        }
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(k.app(), permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void showSystemPermissionsSettingDialog(final Activity context) {
        if (mPermissionDialog == null) {
            mPermissionDialog = new AlertDialog.Builder(context)
                    .setTitle("提示信息")
                    .setMessage("当前应用缺少必要权限，该功能暂时无法使用。如若需要，请单击【设置】按钮前往设置中心进行权限授权。")
                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cancelPermissionDialog();
                            context.startActivityForResult(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.parse("package:" + context.getPackageName())), mRequestCode2);
                        }
                    })
                    .setCancelable(false)
                    .create();
        }
        mPermissionDialog.show();
    }

    //关闭对话框
    private void cancelPermissionDialog() {
        if (mPermissionDialog != null) {
            mPermissionDialog.cancel();
            mPermissionDialog = null;
        }

    }

    public interface KPermissionsListener {
        void hasPermissions(Boolean isHas);
    }


}