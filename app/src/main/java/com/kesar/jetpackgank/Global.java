package com.kesar.jetpackgank;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * Global
 *
 * @author andy <br/>
 * create time: 2019/2/20 15:08
 */
public class Global {
    @SuppressLint("StaticFieldLeak")
    private static Global sInstance;

    private Context mContext;

    private Global() {
    }

    public static Global getInstance() {
        if(sInstance == null) {
            synchronized (Global.class) {
                if(sInstance == null) {
                    sInstance = new Global();
                }
            }
        }
        return sInstance;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return mContext;
    }
}
