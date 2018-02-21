package cn.com.dyhdev.lifeassistant.retrofit;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.com.dyhdev.lifeassistant.entity.CourierDean;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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



    public static void doCourierGetRequest(String url, String appkey, String name, String orderId, Callback<ResponseBody> callback){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CourierInterface rf = retrofit.create(CourierInterface.class);

        Call<ResponseBody> call = rf.getCourierInfo(appkey, name, orderId);

        call.enqueue(callback);


    }

    public static void doPhoneGetRequest(String url, String appkey, String phone, Callback<ResponseBody> callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PhoneInterface pf = retrofit.create(PhoneInterface.class);
        Call<ResponseBody> call = pf.getPhoneInfo(phone, appkey);
        call.enqueue(callback);
    }


}
