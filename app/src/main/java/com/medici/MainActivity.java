package com.medici;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.medici.andfix.module.AndFixCallback;
import com.medici.andfix.module.AndFixService;
import com.medici.andfix.util.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements AndFixCallback{

    private ImageView mPictureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // /data/user/0/com.medici/cache
        String cacheDir = getCacheDir().getAbsolutePath();
        Log.e("MrMedici",cacheDir);

        mPictureView = findViewById(R.id.image);

        AndFixService.runAndFixService(this,this);
    }

    private void loadImage(){
        // 加载帅哥 old
        mPictureView.setImageResource(R.drawable.man);
        // 加载靓女 new
//        mPictureView.setImageResource(R.drawable.woman);
    }

    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return 缓存的字节数组
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    @Override
    public String requestPatchUrl() {
        String url = "http://medici-chat.oss-cn-qingdao.aliyuncs.com/portrait/201712/medici.apatch";
        return url;
    }

    @Override
    public void downloadPatch(String patchUrl, String patchPath) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try{
            URL url = new URL(patchUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            //得到输入流
            inputStream = conn.getInputStream();
            //获取自己数组
            byte[] getData = readInputStream(inputStream);
            //文件保存位置
            File patchFile = new File(patchPath);
            outputStream = new FileOutputStream(patchFile);
            outputStream.write(getData);
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            Utils.close(outputStream);
            Utils.close(inputStream);
        }
    }

}
