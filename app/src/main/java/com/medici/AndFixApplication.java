package com.medici;

import android.app.Application;

import com.medici.andfix.AndFixManager;

/**
 * Created by LSMan on 2018/3/2.
 * @function AndFixApplication
 */

public class AndFixApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        AndFixManager.getInstance().initPatch(this);
    }
}
