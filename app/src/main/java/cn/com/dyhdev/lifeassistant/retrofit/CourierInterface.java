package cn.com.dyhdev.lifeassistant.retrofit;

import cn.com.dyhdev.lifeassistant.entity.CourierDean;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.retrofit
 * 文件名:     CourierInterface
 * 作者:       dyh
 * 时间:       2018/2/17 15:32
 * 描述:       快递查询接口
 */

public interface CourierInterface {
    //快递查询接口：http://v.juhe.cn/exp/index?key=key&com=sf&no=575677355677
    //@GET内的参数，是跟在baseURL后面的，所以该URL的具体代码是http://v.juhe.cn/exp/index?key=
    //www.app.net/api/searchtypes/862189/filters?Type=6&SearchText=School
//    @GET("/api/searchtypes/{Id}/filters")
//    void getFilterList(@Path("Id") long customerId,
//                       @Query("Type") String responseType,
//                       @Query("SearchText") String searchText,
//                       Callback<FilterResponse> filterResponse);
    @GET("/exp/index")
    Call<ResponseBody> getCourierInfo(@Query("key") String key,
                                      @Query("com") String name,
                                      @Query("no") String orderId);

}
