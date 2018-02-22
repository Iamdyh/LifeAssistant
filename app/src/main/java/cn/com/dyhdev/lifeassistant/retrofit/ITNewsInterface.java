package cn.com.dyhdev.lifeassistant.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.retrofit
 * 文件名:     ITNewsInterface
 * 作者:       dyh
 * 时间:       2018/2/22 17:27
 * 描述:       IT资讯接口
 */

public interface ITNewsInterface {
    //url:	http://api.tianapi.com/it/?key=APIKEY&num=10
    @GET("/it/")
    Call<ResponseBody> getITNewsData(@Query("key") String key,
                                     @Query("num") String num);
}
