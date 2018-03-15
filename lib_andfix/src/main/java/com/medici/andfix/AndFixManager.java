package com.medici.andfix;

import android.content.Context;

import com.alipay.euler.andfix.patch.PatchManager;
import com.medici.andfix.util.Utils;

import java.io.IOException;

/**
 * Created by MrMedici on 2018/2/27.
 * @function 管理AndFix所有的Api
 */

public class AndFixManager {

    private static AndFixManager INSTANCE = null;

    private PatchManager mPatchManager = null;

    private AndFixManager(){}

    public static AndFixManager getInstance(){
        if(INSTANCE == null){
            synchronized (AndFixManager.class){
                if(INSTANCE == null){
                    INSTANCE = new AndFixManager();
                }
            }
        }

        return INSTANCE;
    }

    /**
     * 初始化AndFix方法
     * @param context 上下文对象
     */
    public void initPatch(Context context){
        mPatchManager = new PatchManager(context);
        mPatchManager.init(Utils.getVersionName(context));
        mPatchManager.loadPatch();
    }

    /**
     * 加载我们的Patch文件
     * @param path Patch文件路径
     */
    public void addPatch(String path){
        try {
            if(mPatchManager != null){
                mPatchManager.addPatch(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
