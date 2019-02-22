package com.kesar.jetpackgank;

import android.app.Application;

/**
 * GankApplication
 *
 * @author andy <br/>
 * create time: 2019/2/20 09:50
 */
public class GankApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Global.getInstance().setContext(this);
    }
}
