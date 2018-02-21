package cn.com.dyhdev.lifeassistant.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.retrofit
 * 文件名:     PhoneInterface
 * 作者:       dyh
 * 时间:       2018/2/21 13:08
 * 描述:       手机号归属地查询接口
 */

public interface PhoneInterface {
    //手机号查询url:http://apis.juhe.cn/mobile/get?phone=13429667914&key=您申请的KEY

    @GET("/mobile/get")
    Call<ResponseBody> getPhoneInfo(@Query("phone") String phone,
                                    @Query("key") String key);

}
