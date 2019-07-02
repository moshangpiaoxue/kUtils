package com.libs.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.libs.utils.appUtils.PermissionUtil;
import com.libs.utils.logUtils.LogUtil;

import java.util.List;



/**
 * @ author：mo
 * @ data：2018/7/13：15:19
 * @ 功能：权限管理activity基类
 */
public class KPermissionsActivity extends KSlidingCloseActivity {
//    private String[] permissions;
    /**
     * 权限申请结果识别码
     */
    public final int REQUEST_CODE_PERMISSION = 0x00099;
    public final int REQUEST_CODE_PERMISSION2 = 0x00098;

    protected void requestPermission(String permissions) {
        String[] strings = new String[]{permissions};
        requestPermission(strings, true);
    }

    /**
     * 请求权限
     *
     * @param permissions 请求的权限
     */
    protected void requestPermission(String[] permissions) {
        requestPermission(permissions, true);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (permissions != null && permissions.length != 0) {
//            requestPermission(permissions);
//        }
//    }

    /**
     * 请求权限
     *
     * @param permissions 请求的权限
     */
    protected void requestPermission(List<String> permissions) {
        String[] strings = new String[permissions.size()];
        requestPermission(permissions.toArray(strings));
    }

    protected void requestPermission(String[] permissions, boolean showSystemSetting) {
//        this.permissions = permissions;
        PermissionUtil.INSTANCE.requestPermission(mActivity, permissions, REQUEST_CODE_PERMISSION,
                showSystemSetting, new PermissionUtil.KPermissionsListener() {
                    @Override
                    public void hasPermissions(Boolean isHas) {
                        if (isHas) {
                            requestPermissionSuccess(REQUEST_CODE_PERMISSION);
                        } else {
                            requestPermissionFail(REQUEST_CODE_PERMISSION, null);
                        }
                    }
                });

    }

    /**
     * 系统请求权限回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            PermissionUtil.INSTANCE.onRequestPermissionsResult(mActivity, requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PERMISSION2) {
            PermissionUtil.INSTANCE.onActivityResult(mActivity, REQUEST_CODE_PERMISSION);
        }
    }

    /**
     * 获取权限成功
     */
    protected void requestPermissionSuccess(int requestCode) {
        LogUtil.i("获取权限成功=" + requestCode);
    }

    /**
     * 权限获取失败
     */
    protected void requestPermissionFail(int requestCode, String[] deniedPermissions) {
        LogUtil.i("权限获取失败=" + requestCode);
    }


}
