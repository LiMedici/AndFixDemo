package com.medici.andfix.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by MrMedici on 2018/2/27.
 * @function AndFix需要用到的工具类
 */

public class Utils {

    /**
     * 获取应用程序的versionName
     * @param context 上下文对象
     * @return versionName
     */
    public static String getVersionName(Context context){
        String versionName = "1.0.0";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(),0);
            versionName = pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 关闭流
     * @param closeable 流
     */
    public static void close(Closeable closeable){
        if(closeable != null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
