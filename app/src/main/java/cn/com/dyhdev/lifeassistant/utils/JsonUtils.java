package cn.com.dyhdev.lifeassistant.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.com.dyhdev.lifeassistant.entity.CourierDean;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.utils
 * 文件名:     JsonUtils
 * 作者:       dyh
 * 时间:       2018/2/21 11:14
 * 描述:       解析Json数据工具类
 */

public class JsonUtils {

    private static final String TAG = "JsonUtils";

    /**
     * 解析快递的json数据
     * @return
     */
    public static List<CourierDean> parsingCourierJson(String string){
        List<CourierDean> tempList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(string);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            if(jsonObject != null){
                JSONArray jsonArray = jsonResult.getJSONArray("list");
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject json = (JSONObject)jsonArray.get(i);
                    CourierDean data = new CourierDean();
                    data.setRemark(json.getString("remark"));
                    data.setZone(json.getString("zone"));
                    data.setDatetime(json.getString("datetime"));
                    tempList.add(data);
                }
                return tempList;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] parsingPhoneJson(String string){
        String[] text = new String[5];
        try {
            JSONObject json = new JSONObject(string);
            JSONObject jsonObject = json.getJSONObject("result");
            if(jsonObject != null){
                text[0] = jsonObject.getString("province");
                text[1] = jsonObject.getString("city");
                text[2] = jsonObject.getString("areacode");
                text[3] = jsonObject.getString("zip");
                text[4] = jsonObject.getString("company");
                return text;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
