package com.libs.utils.systemUtils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;

import com.libs.k;
import com.libs.modle.constants.KConstans;
import com.libs.utils.appUtils.AppInfoUtil;
import com.libs.utils.dataUtil.date.DateUtil;
import com.libs.utils.logUtils.LogUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * @ author：mo
 * @ data：2017/6/13：11:03
 * @ 功能：相机工具类
 */
public class CameraUtil {

    /**
     * 检测相机是否存在 、摄像头硬件是否可用
     */
    public static boolean isExistCamera() {
        if (k.app().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否有后置摄像头
     */
    public static boolean hasBackFacingCamera() {
        return checkCameraFacing(Camera.CameraInfo.CAMERA_FACING_BACK);
    }

    /**
     * 判断是否有前置摄像头
     */
    public static boolean hasFrontFacingCamera() {
        return checkCameraFacing(Camera.CameraInfo.CAMERA_FACING_FRONT);
    }

    // 是否是Android 10以上手机
    private static boolean isAndroidQ = Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q;

    /**
     * 创建图片地址uri,用于保存拍照后的照片 Android 10以后使用这种方法
     */
    private static Uri createImageUri() {
        String status = Environment.getExternalStorageState();
        // 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return k.app().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
        } else {
            return k.app().getContentResolver().insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, new ContentValues());
        }
    }

    /**
     * 开启拍照 跳系统相机并返回图片的uri
     *
     * @param mActivity
     * @return
     */
    public static Uri actionPhoneTake(Activity mActivity) {

        Uri imageUri;
        File outImage = new File(k.app().getExternalCacheDir(), DateUtil.getM() + "_phone.img");
        try {
            if (outImage.exists()) {
                outImage.delete();
            }
            outImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (isAndroidQ) {
            // 适配android 10
            imageUri = createImageUri();
        } else if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(mActivity, AppInfoUtil.getAppInfo().getPackageName() + ".fileprovider", outImage);

        } else {
            imageUri = Uri.fromFile(outImage);
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        mActivity.startActivityForResult(intent, KConstans.MEDIA_TAKE_PIC);
        return imageUri;
    }

    public static void actionPhoneChoose(Activity mActivity) {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        mActivity.startActivityForResult(intent, KConstans.MEDIA_CHOOSE_PIC);
    }

    public static Uri actionMediaTakeVideo(Activity mActivity) {

        Uri videoUri;
        File outVideo = new File(k.app().getExternalCacheDir(), DateUtil.getM() + "_video.mp4");
        try {
            if (outVideo.exists()) {
                outVideo.delete();
            }
            outVideo.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            videoUri = FileProvider.getUriForFile(mActivity, AppInfoUtil.getAppInfo().getPackageName() + ".fileprovider", outVideo);
            LogUtil.i(AppInfoUtil.getAppInfo().getPackageName() + ".fileprovider");

        } else {
            videoUri = Uri.fromFile(outVideo);
        }

        //        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
        //        mActivity.startActivityForResult(intent, KConstans.MEDIA_TAKE_PIC);
        Intent intent = new Intent();
        intent.setAction("android.media.action.VIDEO_CAPTURE");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
        mActivity.startActivityForResult(intent, KConstans.MEDIA_TAKE_VIDEO);
        return videoUri;
    }

    /**
     * 处理选择图片后的结果
     *
     * @param data
     * @return
     */
    public static String handlerImageChooseResult(Intent data) {
        if (Build.VERSION.SDK_INT >= 19) {
            //            4.4(19)版本以上从相册选取图片后处理返回图片
            return handlerImageAfter19(data);
        } else {
            //            4.4(19)版本以下从相册选取图片后处理返回图片
            return CameraUtil.getImagePath(data.getData(), null);
        }
    }

    /**
     * 4.4(19)版本以上从相册选取图片后处理返回图片
     *
     * @param data
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String handlerImageAfter19(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        //如果是document类型的uri，则通过document id来处理
        if (DocumentsContract.isDocumentUri(k.app(), uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = CameraUtil.getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = CameraUtil.getImagePath(contentUri, null);
            }
            //如果是content类型的URI，则使用普通方式处理
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = CameraUtil.getImagePath(uri, null);
            //如果是file类型的URI，则直接获取图片路径
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        return imagePath;
    }

    /**
     * 通过Uri和selection 获取真实的图片路径
     *
     * @param uri
     * @param selection
     * @return
     */
    public static String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = k.app().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 判断是否有某朝向的摄像头
     *
     * @param facing 摄像头朝向, 前置或后置
     */
    private static boolean checkCameraFacing(final int facing) {
        final int cameraCount = Camera.getNumberOfCameras();
        Camera.CameraInfo info = new Camera.CameraInfo();
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, info);
            if (facing == info.facing) {
                return true;
            }
        }
        return false;
    }

}
