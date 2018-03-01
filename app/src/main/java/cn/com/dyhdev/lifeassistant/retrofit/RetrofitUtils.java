package cn.com.dyhdev.lifeassistant.retrofit;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.dyhdev.lifeassistant.entity.CourierDean;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.retrofit
 * 文件名:     RetrofitUtils
 * 作者:       dyh
 * 时间:       2018/2/17 15:29
 * 描述:       网络请求工具类
 */

public class RetrofitUtils {

    private static final String TAG = "RetrofitUtils";


    /**
     * 快递查询get请求
     * @param url
     * @param appkey
     * @param name
     * @param orderId
     * @param callback
     */
    public static void doCourierGetRequest(String url, String appkey, String name, String orderId, Callback<ResponseBody> callback){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CourierInterface rf = retrofit.create(CourierInterface.class);

        Call<ResponseBody> call = rf.getCourierInfo(appkey, name, orderId);

        call.enqueue(callback);


    }

    /**
     * 手机号归属地查询get请求
     * @param url
     * @param appkey
     * @param phone
     * @param callback
     */
    public static void doPhoneGetRequest(String url, String appkey, String phone, Callback<ResponseBody> callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PhoneInterface pf = retrofit.create(PhoneInterface.class);
        Call<ResponseBody> call = pf.getPhoneInfo(phone, appkey);
        call.enqueue(callback);
    }

    /**
     * IT资讯get请求
     * @param url
     * @param appkey
     * @param num
     * @param callback
     */
    public static void doITNewsGetRequest(String url, String appkey, String num, Callback<ResponseBody> callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ITNewsInterface it = retrofit.create(ITNewsInterface.class);
        Call<ResponseBody> call = it.getITNewsData(appkey, num);
        call.enqueue(callback);
    }

    /**
     * 美女图片get请求
     * @param url
     * @param welfare
     * @param num
     * @param callback
     */
    public static void doBeautyImageGetRequest(String url, String welfare, int num, Callback<ResponseBody> callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BeautyInterface bf = retrofit.create(BeautyInterface.class);
        Call<ResponseBody> call = bf.getBeautyImage(welfare, num);
        call.enqueue(callback);
    }

    /**
     * 版本更新get请求
     * @param url
     * @param callback
     */
    public static void doVersionGetRequest(String url, Callback<ResponseBody> callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        VersionUpdateInterface vf = retrofit.create(VersionUpdateInterface.class);
        Call<ResponseBody> call = vf.getVersionInfo();
        call.enqueue(callback);
    }

    public static void doDownloadApkGetRequest(String baseUrl, String url, Callback<ResponseBody> callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DownloadApkInterface df = retrofit.create(DownloadApkInterface.class);
        Call<ResponseBody> call = df.downloadApk(url);
        call.enqueue(callback);

    }

    /**
     * 语音聊天Post请求
     * @param url
     * @param json
     * @param callback
     */
    public static void doChatPostRequest(String url, String json, Callback<ResponseBody> callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .build();
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        ChatInterface cf = retrofit.create(ChatInterface.class);
        Call<ResponseBody> call = cf.getChatMessage(body);
        call.enqueue(callback);
    }

}
