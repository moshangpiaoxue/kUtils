package com.kutils;


import com.libs.KApplication;
import com.mo.kbuglylib.BuglyUtil;


/**
 * @ author：mo
 * @ data：2019/5/30:18:20
 * @ 功能：
 */
public class AApplication extends KApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        BuglyUtil.InitBugly(getApplicationContext(),"993b585d6a",true);
    }
}
