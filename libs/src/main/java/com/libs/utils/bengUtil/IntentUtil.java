package com.libs.utils.bengUtil;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresPermission;
import android.webkit.MimeTypeMap;

import com.libs.k;
import com.libs.utils.appUtils.PermissionUtil;
import com.libs.utils.dataUtil.StringUtil;
import com.libs.utils.fileUtil.FileProviderUtils;
import com.libs.utils.fileUtil.FileUtil;
import com.libs.utils.logUtils.LogUtil;

import java.io.File;


/**
 * description: intent工具类
 * autour: mo
 * date: 2017/9/15 0015 10:02
 */
public class IntentUtil {

    private IntentUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取「安装应用」的意图
     *
     * @return 意图
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @RequiresPermission(value = Manifest.permission.REQUEST_INSTALL_PACKAGES)
    public static Intent getInstallAppIntent(final String filePath) {
        return StringUtil.isEmpty(filePath) ? null : getInstallAppIntent(FileUtil.getFileByPath(filePath));
    }

    /**
     * 获取「安装应用」的意图
     *
     * @param apkFile APK 文件
     * @return 意图
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @RequiresPermission(value = Manifest.permission.REQUEST_INSTALL_PACKAGES)
    public static Intent getInstallAppIntent(File apkFile) {
        if (apkFile == null || !apkFile.exists() || !apkFile.isFile()) {
            return null;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            uri = FileProviderUtils.getUriForFile(apkFile);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(apkFile);
        }
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        return intent;
    }


    /**
     * 获取 「卸载 应用」的意图
     *
     * @param packageName 包名
     * @return 卸载 App 的意图
     */
    public static Intent getUninstallAppIntent(final String packageName) {
        if (StringUtil.isEmpty(packageName)) {
            return null;
        }
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + packageName));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }


    /**
     * 获取 「打开 应用」的意图
     *
     * @param packageName 包名
     * @return 打开 App 的意图
     */
    public static Intent getLaunchAppIntent(final String packageName) {
        return k.app().getPackageManager().getLaunchIntentForPackage(packageName);
    }

    /**
     * 获取跳转「应用组件」的意图
     *
     * @param packageName 应用包名
     * @param className   应用组件的类名
     * @return 意图
     */
    public static Intent getComponentIntent(String packageName, String className) {
        return new Intent(Intent.ACTION_VIEW)
                .setComponent(new ComponentName(packageName, className))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取跳转「设置界面」的意图
     *
     * @param packageName 包名
     * @return App 具体设置的意图
     */
    public static Intent getSettingAppIntent(final String packageName) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("package:" + packageName));
        return intent;
//        return new Intent(Settings.ACTION_SETTINGS) .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取跳转「应用详情」的意图
     *
     * @param packageName 应用包名
     * @return 意图
     */
    public static Intent getAppDetailsSettingsIntent(String packageName) {
        return new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(Uri.parse("package:" + packageName))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取跳转「应用列表」的意图
     *
     * @return 意图
     */
    public static Intent getAppsIntent() {
        return new Intent(Settings.ACTION_APPLICATION_SETTINGS)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取跳转「无障碍服务设置」的意图
     *
     * @return 意图
     */
    public static Intent getAccessibilitySettingIntent() {
        return new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取跳转「网络设置」的意图
     *
     * @return
     */
    public static Intent getSettingNetIntent() {
        return new Intent(Settings.ACTION_WIRELESS_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取跳转「桌面主页」的意图
     *
     * @return 意图
     */
    public static Intent getHomeIntent() {
        return new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME);
    }

    /**
     * 获取关机的意图
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.SHUTDOWN" />}</p>
     *
     * @return 关机的意图
     */
    public static Intent getShutdownIntent() {
        return new Intent(Intent.ACTION_SHUTDOWN);
    }

    /**
     * 获取「分享文本」的意图
     *
     * @param content 分享文本
     * @return 分享文本的意图
     */
    public static Intent getShareTextIntent(final String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        return intent;
    }

    /**
     * 获取「分享图片」的意图
     *
     * @param content 分享文本
     * @param uri     图片 uri
     * @return 分享图片的意图
     */
    public static Intent getShareImageIntent(final String content, final Uri uri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/*");
        return intent;
    }

    /**
     * 获取跳转「选择...」的意图
     *
     * @param type mimeType 类型
     * @return 意图
     */
    public static Intent getPickIntent(String type) {
        return new Intent(Intent.ACTION_GET_CONTENT)
                .setType(type)
                .addCategory(Intent.CATEGORY_OPENABLE);
    }

    /**
     * 获取跳转「选择文件」的意图
     *
     * @return 意图
     */
    public static Intent getPickFileIntent() {
        return getPickIntent("file/*");
    }

    /**
     * 获取跳转「选择文件」的意图, 指定文件扩展名
     *
     * @param fileExtension 文件扩展名
     * @return 意图
     */
    public static Intent getPickFileIntent(String fileExtension) {
        String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
        if (type == null) {
            return null;
        }
        return getPickIntent(type);
    }

    /**
     * 获取跳转「选择图片」的意图
     *
     * @return 意图
     */
    public static Intent getPickPhotoIntent() {
        return getPickIntent("image/*");
    }

    /**
     * 获取跳转「选择视频」的意图
     *
     * @return 意图
     */
    public static Intent getPickVideoIntent() {
        return getPickIntent("video/*");
    }

    /**
     * 获取跳转「选择音频」的意图
     *
     * @return 意图
     */
    public static Intent getPickAudioIntent() {
        return getPickIntent("audio/*");
    }

    /**
     * 获取跳转「系统剪裁」的意图
     *
     * @param inputFile  剪裁图片文件
     * @param outputFile 输出图片文件
     * @param aspectX    输出图片宽高比中的宽
     * @param aspectY    输出图片宽高比中的高
     * @param outputX    输出图片的宽
     * @param outputY    输出图片的高
     * @return 意图
     */
    public static Intent getCropPhotoIntent(File inputFile, File outputFile, int aspectX, int aspectY, int outputX, int outputY) {
        Uri inputUri;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            inputUri = FileProviderUtils.getUriForFile(inputFile);
        } else {
            inputUri = Uri.fromFile(inputFile);
        }
        return getCropPhotoIntent(inputUri, Uri.fromFile(outputFile), aspectX, aspectY, outputX, outputY);
    }


    /**
     * 获取跳转「系统剪裁」的意图
     *
     * @param inputUri  剪裁图片文件的 Uri
     * @param outputUri 输出图片文件的 Uri
     * @param aspectX   输出图片宽高比中的宽
     * @param aspectY   输出图片宽高比中的高
     * @param outputX   输出图片的宽
     * @param outputY   输出图片的高
     * @return 意图
     */
    public static Intent getCropPhotoIntent(Uri inputUri, Uri outputUri, int aspectX, int aspectY, int outputX, int outputY) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(inputUri, "image/*");
        intent.putExtra("crop", "true");
        // 指定输出宽高比
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        // 指定输出宽高
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        // 指定输出路径和文件类型
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        if (outputUri.getPath().endsWith(".jpg") || outputUri.getPath().endsWith(".jpeg")) {
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        } else if (outputUri.getPath().endsWith(".png") || inputUri.getPath().endsWith(".png")) {
            intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        } else {
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        }
        intent.putExtra("return-data", false);
        return intent;
    }

    /**
     * 获取跳转「拨号界面」的意图
     *
     * @return 意图
     */
    public static Intent getDialIntent() {
        return new Intent(Intent.ACTION_DIAL)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取跳转「拨号界面」的意图
     *
     * @param phoneNumber 电话号码
     * @return 意图
     */
    public static Intent getDialIntent(String phoneNumber) {
        return new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取跳转「拨打电话」的意图, 即直接拨打电话
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.CALL_PHONE"/>}</p>
     *
     * @param phoneNumber 电话号码
     * @return 意图
     */
    @RequiresPermission(value = Manifest.permission.CALL_PHONE)
    public static Intent getCallIntent(String phoneNumber) {
        if (!PermissionUtil.INSTANCE.checkSelfPermission(Manifest.permission.CALL_PHONE)) {
            LogUtil.i("CALL_PHONE权限");
            return null;
        }
        return new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }


    /**
     * 获取跳转「发送短信」的意图
     *
     * @param phoneNumber 电话号码
     * @param content     预设内容
     * @return 意图
     */
    public static Intent getSendSmsIntent(String phoneNumber, String content) {
        return new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber))
                .putExtra("sms_body", content)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }


    /**
     * 获取跳转「联系人」的意图
     *
     * @return 意图
     */
    public static Intent getContactsIntent() {
        return new Intent(Intent.ACTION_VIEW)
                .setData(ContactsContract.Contacts.CONTENT_URI);
    }

    /**
     * 获取跳转「联系人详情」的意图
     *
     * @param contactId 联系人的 contactId
     * @param lookupKey 联系人的 lookupKey
     * @return 意图
     */
    public static Intent getContactDetailIntent(long contactId, String lookupKey) {
        Uri data = ContactsContract.Contacts.getLookupUri(contactId, lookupKey);
        return new Intent(Intent.ACTION_VIEW)
                .setDataAndType(data, ContactsContract.Contacts.CONTENT_ITEM_TYPE);
    }

    /**
     * 获取跳转「系统相机」的意图
     *
     * @param outputFile 拍摄图片的输入文件对象
     * @return 意图
     */
    public static Intent getTakePhotoIntent(File outputFile) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            return getCaptureIntent(FileProviderUtils.getUriForFile(outputFile));
        } else {
            return getCaptureIntent(Uri.fromFile(outputFile));
        }
    }

    /**
     * 获取跳转「系统相机」的意图
     *
     * @param outUri 输出的 uri
     * @return 拍照的意图
     */
    public static Intent getCaptureIntent(final Uri outUri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
        }
        return intent;
    }

    /**
     * 获取跳转「 Wifi 设置」的意图
     *
     * @return 意图
     */
    public static Intent getWifiSettingIntent() {
        return new Intent(Settings.ACTION_WIFI_SETTINGS)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
