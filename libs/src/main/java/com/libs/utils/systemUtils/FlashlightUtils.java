package com.libs.utils.systemUtils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;

import com.libs.k;


/**
 * @ author：mo
 * @ data：2019/3/19:13:25
 * @ 功能：闪光灯工具类（不用必须调close方法）、在Camera被持有的时候，也就是开启相机预览或者扫描二维码的时候，不能生效
 * FlashlightUtils.INSTANCE.open();
 * FlashlightUtils.INSTANCE.close();
 */
public enum FlashlightUtils {
    /**
     * 枚举单例
     */
    INSTANCE;
    /**
     *
     */
    private boolean flashlightAvailable;
    private Camera camera;
    private CameraManager cameraManager;
    private String cameraId;

    {
        flashlightAvailable = k.app().getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cameraManager = (CameraManager) k.app().getApplicationContext().getSystemService(Context.CAMERA_SERVICE);
            try {
                if (cameraManager != null) {
                    cameraId = cameraManager.getCameraIdList()[0];
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 是否有闪光灯
     */
    public boolean isFlashlightAvailable() {
        return flashlightAvailable;
    }

    public boolean actionFlashLight() {
        if (status) {
            status = !close();
        } else {
            status = open();
        }
        return status;
    }

    private boolean status = false;

    /**
     * 开启闪光灯
     *
     * @return 是否开启成功
     */
    public boolean open() {
        if (!flashlightAvailable) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                cameraManager.setTorchMode(cameraId, true);
                return true;
            } catch (CameraAccessException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            try {
                camera = Camera.open();
                camera.startPreview();
                Camera.Parameters parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    /**
     * 关闭闪光灯
     *
     * @return 是否关闭成功
     */
    public boolean close() {
        if (!flashlightAvailable) {
            return false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                cameraManager.setTorchMode(cameraId, false);
                return true;
            } catch (CameraAccessException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            try {
                if (camera != null) {
                    Camera.Parameters parameters = camera.getParameters();
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    camera.setParameters(parameters);
                    camera.stopPreview();
                    camera.release();
                    camera = null;
                } else {
                    return false;
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}
