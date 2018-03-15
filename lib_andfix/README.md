###AndFix Library集成步骤
1、AndroidManifest文件配置
<uses-permission android:name="android.permission.INTERNET"/>
<service android:name="com.medici.andfix.module.AndFixService"/>

2、实现接口方法,获取patch文件地址,以及执行下载patch文件业务
@Override
public String requestPatchUrl() {
    // 执行在工作线程
}

@Override
public void downloadPatch(String patchUrl, String patchPath) {
    // 执行在工作线程
}

3、启动AndFix热修复服务
AndFixService.runAndFixService(this,this);

