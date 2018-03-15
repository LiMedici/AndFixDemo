package com.medici.andfix.module;

import android.support.annotation.WorkerThread;

import java.io.Serializable;

/**
 * Created by MrMedici on 2018/2/28.
 * @function 用于业务逻辑层返回是否有新的PATCH文件,以及获取下载地址
 * {@link AndFixCallback}
 */

public interface AndFixCallback extends Serializable{

    public static final long serialVersionUID = -4134321512591787087L;

    /**
     * 返回Patch文件地址 Http,运行在工作线程
     * @return 返回值不为Null,服务器存在新的patch文件,等待修复
     */
    @WorkerThread
    String requestPatchUrl();

    /**
     * 下载Patch文件,运行在工作线程
     * @param patchUrl patch文件Url
     * @param patchPath 文件保存地址
     */
    @WorkerThread
    void downloadPatch(String patchUrl, String patchPath);
}
