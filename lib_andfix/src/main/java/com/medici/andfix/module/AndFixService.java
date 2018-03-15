package com.medici.andfix.module;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.medici.andfix.AndFixManager;

import java.io.File;

/**
 * Created by MrMedici on 2018/2/28.
 * @function 启动服务 1、检查有无Apatch文件 2、下载Apatch文件 3、加载Apatch文件
 */
public class AndFixService extends IntentService{

    /**
     * 回调对象的Key
     */
    public static final String KEY_CALLBACK = "KEY_CALLBACK";
    /**
     * patch文件后缀名
     */
    private static final String FILE_END = ".apatch";
    /**
     * patch文件存储的文件夹
     */
    private static final String PATCH = "patch";
    /**
     * patch文件存储路径
     */
    private String mPatchPath;

    /**
     * patch文件存储目录
     */
    private String mPatchDir;

    /**
     * 回调对象
     */
    private AndFixCallback mCallback;

    public AndFixService() {
        super("AndFixService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    /**
     * 初始化Patch文件的路径
     */
    private void init() {
        // 初始化AndFix
        AndFixManager.getInstance().initPatch(getApplicationContext());
        mPatchDir =  getPatchDir(getApplicationContext());
        File file = new File(mPatchDir);
        if(file == null || !file.exists()){
            // 文件不存在
            file.mkdirs();
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        mCallback = (AndFixCallback) intent.getSerializableExtra(KEY_CALLBACK);
        String patchUrl = mCallback.requestPatchUrl();
        if(TextUtils.isEmpty(patchUrl)){
            stopSelf();
        }

        // 根据Url下载patch文件到指定目标目录中 同步调用
        mCallback.downloadPatch(patchUrl,getPatchPath());
        //文件保存位置
        File patchFile = new File(getPatchPath());
        if(patchFile.exists() && patchFile.length() > 0){
            // 加载patch文件
            AndFixManager.getInstance().addPatch(patchFile.getAbsolutePath());
        }
    }

    /**
     * 获取patch文件的路径
     */
    private String getPatchPath(){
        return mPatchDir
                .concat(File.separator)
                .concat(String.valueOf(System.currentTimeMillis()))
                .concat(FILE_END);
    }

    /**
     * 获取patch文件的目录
     */
    private String getPatchDir(Context context){
        String rootPath = context.getApplicationContext().getCacheDir().getAbsolutePath();
        return rootPath.concat(File.separator)
                .concat(PATCH);
    }

    /**
     * 暴露启动AndFix热更新的服务
     * @param context 上下文
     * @param callback 回调接口
     */
    public static void runAndFixService(Context context,AndFixCallback callback){
        Intent intent = new Intent(context, AndFixService.class);
        // 传入回调对象 实现AndFixCallback接口
        intent.putExtra(AndFixService.KEY_CALLBACK,callback);
        // 启动AndFix热修复服务
        context.startService(intent);
    }
}
