package com.libs.utils.viewUtil;


import android.graphics.drawable.Drawable;
import android.text.Html;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * description: 富文本工具类
 * autour: mo
 * date: 2017/7/27 0027 11:15
 */
public class RichTextUtil {
    /**
     * 处理textview加载富文本不显示图片
     * @return
     */
    public static Html.ImageGetter getImageGetter() {
        Html.ImageGetter imgGetter = new Html.ImageGetter() {
            public Drawable getDrawable(String source) {

                Drawable drawable = null;
                URL url;
                try {
                    url = new URL(source);
                    Drawable.createFromStream(url.openStream(), "");
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());

                return drawable;
            }
        };
        return imgGetter;
    }
    /**
     *
     * @param imageUrl
     * @return
     */
    public Drawable getImageFromNetwork(String imageUrl) {
        URL myFileUrl = null;
        Drawable drawable = null;
        try {
            myFileUrl = new URL(imageUrl);


            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);

            conn.connect();
            InputStream is = conn.getInputStream();
            drawable = Drawable.createFromStream(is, null);

            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return drawable;
    }
}
