package com.libs.utils.dataUtil.dealUtil;//package mo.klib.utils.dataUtil.dealUtil;
//
//
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * @ author：mo
// * @ data：2018/3/28 0028
// * @ 功能：
// */
//public class GsonUtil {
//
//
//    private static Gson gson = null;
//
//    static {
//        if (gson == null) {
//            gson = new Gson();
//        }
//    }
//
//
//    private GsonUtil() {
//    }
//
//    public static Gson getGson() {
//        return gson;
//    }
//
//    /**
//     * 将object对象转成json字符串
//     *
//     * @param object
//     * @return
//     */
//    public static String GsonString(Object object) {
//        String gsonString = null;
//        if (gson != null) {
//            gsonString = gson.toJson(object);
//        }
//        return gsonString;
//    }
//
//
//    /**
//     * 将gsonString转成泛型bean
//     *
//     * @param gsonString
//     * @param cls
//     * @return
//     */
//    public static <T> T GsonToBean(String gsonString, Class<T> cls) {
//        T t = null;
//        if (gson != null) {
//            t = gson.fromJson(gsonString, cls);
//        }
//        return t;
//    }
//
//    public static <T> T GsonToBean(String gsonString) {
//        T aa = null;
//        Type localType = new TypeToken<T>() {
//        }.getType();
//        if (gson != null) {
//            aa = gson.fromJson(gsonString, localType);
//        }
//
//        return aa;
//    }
//
//    /**
//     * 转成list
//     * 泛型在编译期类型被擦除导致报错
//     *
//     * @param gsonString
//     * @param cls
//     * @return
//     */
//    public static <T> List<T> GsonToList(String gsonString, Class<T> cls) {
//        List<T> list = null;
//        if (gson != null) {
//            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
//            }.getType());
//        }
//        return list;
//    }
//
//
//    /**
//     * 转成list
//     * 解决泛型问题
//     *
//     * @param json
//     * @param cls
//     * @param <T>
//     * @return
//     */
//    public static <T> List<T> jsonToList(String json, Class<T> cls) {
//        Gson gson = new Gson();
//        List<T> list = new ArrayList<T>();
//        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
//        for (final JsonElement elem : array) {
//            list.add(gson.fromJson(elem, cls));
//        }
//        return list;
//    }
//
//
//    /**
//     * 转成list中有map的
//     *
//     * @param gsonString
//     * @return
//     */
//    public static <T> List<Map<String, T>> GsonToListMaps(String gsonString) {
//        List<Map<String, T>> list = null;
//        if (gson != null) {
//            list = gson.fromJson(gsonString,
//                    new TypeToken<List<Map<String, T>>>() {
//                    }.getType());
//        }
//        return list;
//    }
//
//
//    /**
//     * 转成map的
//     *
//     * @param gsonString
//     * @return
//     */
//    public static <T> Map<String, T> GsonToMaps(String gsonString) {
//        Map<String, T> map = null;
//        if (gson != null) {
//            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
//            }.getType());
//        }
//        return map;
//    }
//
//}