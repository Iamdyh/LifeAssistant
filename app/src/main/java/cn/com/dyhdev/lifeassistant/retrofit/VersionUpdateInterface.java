package cn.com.dyhdev.lifeassistant.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.retrofit
 * 文件名:     VersionUpdateInterface
 * 作者:       dyh
 * 时间:       2018/3/1 14:46
 * 描述:       版本更新接口类
 */

public interface VersionUpdateInterface {

    @GET("/dyh/config.json")
    Call<ResponseBody> getVersionInfo();
}
