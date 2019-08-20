package com.libs;

import android.app.Application;
import android.content.Context;

import com.libs.modle.annotation.ViewInjector;
import com.libs.modle.annotation.ViewInjectorImpl;
import com.libs.utils.appUtils.activityUtil.ActivitysUtil;
import com.libs.utils.task.control.KTaskController;
import com.libs.utils.task.control.KTaskControllerImpl;

import java.lang.reflect.Method;



/**
 * @ author：mo
 * @ data：2017/11/29：13:39
 * @ 功能：根工具类
 * <p>
 * 备忘：右滑删除集成或写demo
 */
public final class k {
    /**
     * 项目的整体上下文环境
     *
     * @return
     */
    public static Application app() {
        if (Ext.app == null) {
            try {
                // 在IDE进行布局预览时使用
                Class<?> renderActionClass = Class.forName("com.android.layoutlib.bridge.impl.RenderAction");
                Method method = renderActionClass.getDeclaredMethod("getCurrentContext");
                Context context = (Context) method.invoke(null);
                Ext.app = new MockApplication(context);
            } catch (Throwable ignored) {
                throw new RuntimeException("在manifest注册Application，并且在Application#onCreate()方法里 x.Ext.init(app) ");
            }
        }
        return Ext.app;
    }

    public static boolean isDebug() {
        return Ext.debug;
    }

    public static void setTaskController(KTaskController KTaskController) {
        if (Ext.KTaskController == null) {
            Ext.KTaskController = KTaskController;
        }
    }

    /**
     * 注解绑定
     *    k.view().inject(this);
     */
    public static ViewInjector view() {
        if (Ext.viewInjector == null) {
            ViewInjectorImpl.INSTANCE.regist();
        }
        return Ext.viewInjector;
    }

    /**
     * 任务控制器
     *
     * @return
     */
    public static KTaskController task() {
        return Ext.KTaskController;
    }

    public static class Ext {
        private static boolean debug = true;
        private static Application app;
        private static KTaskController KTaskController;
        private static ViewInjector viewInjector;

        private Ext() {
        }









        public static void init(Application app) {
            KTaskControllerImpl.registerInstance();
            if (Ext.app == null) {
                Ext.app = app;
            }
            Ext.app.registerActivityLifecycleCallbacks(ActivitysUtil.getCallBack());
        }

        public static void setDebug(boolean debug) {
            Ext.debug = debug;
        }

        public static void setKTaskController(KTaskController KTaskController) {
            if (Ext.KTaskController == null) {
                Ext.KTaskController = KTaskController;
            }
        }

        public static void setViewInjector(ViewInjector viewInjector) {
            Ext.viewInjector = viewInjector;
        }
    }


    private static class MockApplication extends Application {
        public MockApplication(Context baseContext) {
            this.attachBaseContext(baseContext);
        }
    }
}
