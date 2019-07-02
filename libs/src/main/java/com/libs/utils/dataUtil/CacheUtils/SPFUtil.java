package com.libs.utils.dataUtil.CacheUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;


import com.libs.k;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;



/**
 * @ author：mo
 * @ data：2019/1/14：14:18
 * @ 功能：SharedPreferences工具类
 */
public class SPFUtil {
    private SPFUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 保存图片到shareutils
     * @param key
     * @param imageView
     */
    public static void putImageToShare(String key, ImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        //第一步：将Bitmap压缩成字节数组输出流
        ByteArrayOutputStream byStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byStream);
        //第二步：利用Base64将我们的字节数组输出流转换成String
        byte[] byteArray = byStream.toByteArray();
        String imgString = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
        //第三步：将String保存shareUtils
        putString( key, imgString);
    }


    /**
     * 读取图片
     * @param key
     * @param imageView
     */
    public static void getImageToShare(String key, ImageView imageView) {
        //1.拿到string
        String imgString = getString(key);
        if (!imgString.equals("")) {
            //2.利用Base64将我们string转换
            byte[] byteArray = Base64.decode(imgString, Base64.DEFAULT);
            ByteArrayInputStream byStream = new ByteArrayInputStream(byteArray);
            //3.生成bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(byStream);
            imageView.setImageBitmap(bitmap);
        }
    }
    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "USER_INFO";

    /**
     * 获取SharedPreferences 对象， 没有则会自动新建一个
     *
     * @return
     */
    private static SharedPreferences getSharedPreferences() {
        return k.app().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 存String
     *
     * @param key
     * @param value
     */
    public static void putString(String key, String value) {
        SharedPreferencesCompat.apply(getSharedPreferences().edit().putString(key, value));
    }

    /**
     * 取String
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        return getSharedPreferences().getString(key, "");
    }

    /**
     * 存int
     *
     * @param key
     * @param value
     */
    public static void putInt( String key, int value) {
        SharedPreferencesCompat.apply(getSharedPreferences().edit().putInt(key, value));
    }

    /**
     * 取int
     *
     * @param key
     * @return
     */
    public static int getInt(String key) {
        return getSharedPreferences().getInt(key, 0);
    }

    /**
     * 存boolean
     *
     * @param key
     * @param value
     */
    public static void putBoolean( String key, boolean value) {
        SharedPreferencesCompat.apply(getSharedPreferences().edit().putBoolean(key, value));
    }

    /**
     * 取boolean
     *
     * @param key
     * @return
     */
    public static boolean getBoolean( String key) {
        return getSharedPreferences().getBoolean(key, false);
    }

    /**
     * 存放实体类
     *
     * @param key
     * @param obj
     */
    public static void putBean(String key, Object obj) {
        if (obj instanceof Serializable) {// obj必须实现Serializable接口，否则会出问题
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(obj);
                String string64 = new String(Base64.encode(baos.toByteArray(),
                        0));
                getSharedPreferences().edit().putString(key, string64).commit();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            throw new IllegalArgumentException(
                    "obj 这个实体类必须 implement Serializble");
        }
    }


    /**
     * 取实体类
     *
     * @param key
     */

    public static Object getBean(String key) {
        Object obj = null;
        try {
            String base64 = getSharedPreferences().getString(key, "");
            if (base64.equals("")) {
                return null;
            }
            byte[] base64Bytes = Base64.decode(base64.getBytes(), 1);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            obj = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;

    }

    /**
     * 存任意类型的数据
     *
     * @param key
     * @param object
     */
    public static void put(String key, Object object) {

        SharedPreferences.Editor editor = getSharedPreferences().edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }
    /**
     * 存任意类型的数据
     *
     * @param map
     */
    public static void put( Map<String, Object> map) {
        SharedPreferences.Editor editor =getSharedPreferences().edit();
        Set set = map.keySet();
        for (Iterator iter = set.iterator(); iter.hasNext(); ) {
            String key = (String) iter.next();
            Object object = map.get(key);
            if (object instanceof String) {
                editor.putString(key, (String) object);
            } else if (object instanceof Integer) {
                editor.putInt(key, (Integer) object);
            } else if (object instanceof Boolean) {
                editor.putBoolean(key, (Boolean) object);
            } else if (object instanceof Float) {
                editor.putFloat(key, (Float) object);
            } else if (object instanceof Long) {
                editor.putLong(key, (Long) object);
            } else if (object == null) {
                editor.putString(key, null);
            } else {
                editor.putString(key, object.toString());
            }
        }
        SharedPreferencesCompat.apply(editor);
    }
    /**
     * 取任意类型数据--根据默认值类型
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(String key, Object defaultObject) {
        SharedPreferences sp = getSharedPreferences();

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }
    public static Map getValue(Object obj) {
        Map map = new HashMap();
        Class cla;
        try {
            cla = Class.forName(obj.getClass().getName());
            Method[] m = cla.getMethods();
            for (int i = 0; i < m.length; i++) {
                String method = m[i].getName();
                if (method.startsWith("get")) {
                    try {
                        Object value = m[i].invoke(obj);
//						if (value != null)
//						{
                        String key = method.substring(3);
                        key = key.substring(0, 1).toLowerCase() + key.substring(1);
                        map.put(key, value);
//						}
                    } catch (Exception e) {
                        System.out.println("error:" + method);
                    }
                }
                if (method.startsWith("is")) {
                    try {
                        Object value = m[i].invoke(obj);
//						if (value != null)
//						{
                        String key = method.substring(2);
//							key=key.substring(0,1).toUpperCase()+key.substring(1);
                        map.put(key, value);
//						}
                    } catch (Exception e) {
                        System.out.println("error:" + method);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public static void remove(String key) {
        SharedPreferencesCompat.apply(getSharedPreferences().edit().remove(key));
    }

    /**
     * 清除所有数据
     */
    public static void clear() {
        SharedPreferencesCompat.apply(getSharedPreferences().edit().clear());
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public static boolean contains(String key) {
        return getSharedPreferences().contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @return
     */
    public static Map<String, ?> getAll() {
        return getSharedPreferences().getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }
            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }


}