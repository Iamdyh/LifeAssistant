package cn.com.dyhdev.lifeassistant.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.retrofit
 * 文件名:     DownloadApkInterface
 * 作者:       dyh
 * 时间:       2018/3/1 16:26
 * 描述:       Apk下载接口
 */

public interface DownloadApkInterface {

    @Streaming  //大文件时需要加，不然会OOM
    @GET
    Call<ResponseBody> downloadApk(@Url String url);
}
