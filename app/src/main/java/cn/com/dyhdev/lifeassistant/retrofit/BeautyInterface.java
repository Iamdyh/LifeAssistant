package cn.com.dyhdev.lifeassistant.retrofit;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.retrofit
 * 文件名:     BeautyInterface
 * 作者:       dyh
 * 时间:       2018/2/27 14:08
 * 描述:       美女图片接口
 */

public interface BeautyInterface {
    //干货妹子url: http://gank.io/api/data/福利/50/1
    @GET("/api/data/{welfare}/{num}/1")
    Call<ResponseBody> getBeautyImage(@Path("welfare") String welfare,
                                      @Path("num") int num);

}
