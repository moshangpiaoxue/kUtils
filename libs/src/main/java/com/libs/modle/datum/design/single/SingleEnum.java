package com.libs.modle.datum.design.single;

import java.io.Serializable;


/**
 * @author：mo
 * @data：2018/5/31 0031
 * @功能：单例模式--枚举 枚举天生保证序列化单例。
 * <p>
 * 使用场景：涉及到反序列化创建对象
 */

public enum SingleEnum implements Serializable {
    instance;

    public void login() {
        System.err.print("登录");
    }

    public void loginOut() {
        System.err.print("退出");

    }

}
