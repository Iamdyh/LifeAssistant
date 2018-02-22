package cn.com.dyhdev.lifeassistant.retrofit;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.retrofit
 * 文件名:     ChatInterface
 * 作者:       dyh
 * 时间:       2018/2/21 18:25
 * 描述:       聊天接口
 */

public interface ChatInterface {

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/openapi/api")
    Call<ResponseBody> getChatMessage(@Body RequestBody info);
}
