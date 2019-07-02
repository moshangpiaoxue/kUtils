package com.libs.modle.datum.design.single;

import java.util.HashMap;

/**
 * @author：mo
 * @data：2018/5/31 0031
 * @功能：单例模式--容器管理单例
 */

public class SingleIOC {
    /**
     * 容器
     */
    private static HashMap<String, Object> hashMap = new HashMap<>();
    /**
     * 容器的最大长度
     */
    private static int MAX_SIZE = 3;

    public SingleIOC() {
    }

    public static Object getInstance(String key) {
        Object instance = hashMap.get(key);
        if (instance == null && hashMap.size() < MAX_SIZE) {
            instance = new Single();
            hashMap.put(key, instance);

        }
        return instance;
    }

    /**
     * 单例实例类
     */
    public static class Single {
        public Single() {
        }
    }
}
