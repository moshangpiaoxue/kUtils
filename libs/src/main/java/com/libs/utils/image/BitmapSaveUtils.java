package com.libs.utils.image;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;

import com.libs.k;
import com.libs.utils.dataUtil.StringUtil;
import com.libs.utils.dataUtil.date.DateUtil;
import com.libs.utils.fileUtil.FileDirUtil;
import com.libs.utils.logUtils.LogUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;



/**
 * @ author：mo
 * @ data：2018/12/26
 * @ 功能：
 */
public class BitmapSaveUtils {
    /**
     * 把bitmap以JPG的形式保存到文件里
     *
     * @param bitmap
     * @throws IOException
     */
    public static File saveBitmapToJPG(Bitmap bitmap) throws IOException {
        return saveBitmapToJPG(bitmap, DateUtil.getMS() + ".jpg");
    }

    public static File saveBitmapToJPG(Bitmap bitmap, String name) throws IOException {
        final File photo = new File(FileDirUtil.getAlbumStorageDir("sign"), name);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
        return photo;
    }


    public static String addBitmapToGallery(Bitmap signature, String name) {
        String result = "";
        try {
            File file;
            if (StringUtil.isEmpty(name)) {
                file = BitmapSaveUtils.saveBitmapToJPG(signature, name);
            } else {
                file = BitmapSaveUtils.saveBitmapToJPG(signature);
            }
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(file);
            mediaScanIntent.setData(contentUri);
            k.app().sendBroadcast(mediaScanIntent);
            LogUtil.i("AAAA==" + file.getAbsolutePath());
            result = file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 保存图片
     *
     * @param src
     * @param filepath
     * @param format:[Bitmap.CompressFormat.PNG,Bitmap.CompressFormat.JPEG]
     * @return
     */
    public static boolean saveBitmap(Bitmap src, String filepath, Bitmap.CompressFormat format) {
        boolean rs = false;
        File file = new File(filepath);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (src.compress(format, 100, out)) {
                out.flush();  //写入流
            }
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rs;
    }
}
