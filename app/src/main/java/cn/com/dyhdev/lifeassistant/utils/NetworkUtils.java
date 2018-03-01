package cn.com.dyhdev.lifeassistant.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.utils
 * 文件名:     NetworkUtils
 * 作者:       dyh
 * 时间:       2018/3/1 17:45
 * 描述:       网络状态工具类
 */

public class NetworkUtils {
    public static final int NETWORK_NONE = 0;
    public static final int NETWORK_WIFI = 1;
    public static final int NETWORK_MOBILE = 2;

    public static int getNetworkState(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //wifi
        NetworkInfo.State state = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if(state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING){
            return NETWORK_WIFI;
        }

        //移动网络
        state = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if(state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING){
            return NETWORK_MOBILE;
        }

        return NETWORK_NONE;
    }
}
