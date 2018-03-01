package cn.com.dyhdev.lifeassistant.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.com.dyhdev.lifeassistant.retrofit.ProgressListener;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.utils
 * 文件名:     FileUtils
 * 作者:       dyh
 * 时间:       2018/3/1 17:02
 * 描述:       文件工具类
 */

public class FileUtils {
    private static final String TAG = "FileUtils";

    public static File createFile(Context context){
        File file = null;
        String state = Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED)){
            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/update.apk");
        }else{
            file = new File(context.getCacheDir().getAbsolutePath() + "/update.apk");
        }

        Log.d(TAG, "createFile: " + file.getAbsolutePath());
        return file;
    }

    public static void writeFile2Disk(Response<ResponseBody> response, File file, ProgressListener listener){
        long currentLength = 0;
        OutputStream os = null;
        InputStream is = response.body().byteStream();
        long totalLength = response.body().contentLength();

        try {
            os = new FileOutputStream(file);
            int len;
            byte[] buff = new byte[2048];
            while((len = is.read(buff)) != -1){
                os.write(buff, 0, len);
                currentLength += len;
                Log.d(TAG, "当前进度: " + currentLength );
                listener.onProgress(currentLength, totalLength, currentLength == totalLength);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
