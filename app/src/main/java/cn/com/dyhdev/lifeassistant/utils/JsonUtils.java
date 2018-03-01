package cn.com.dyhdev.lifeassistant.utils;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.com.dyhdev.lifeassistant.entity.Beauty;
import cn.com.dyhdev.lifeassistant.entity.CourierDean;
import cn.com.dyhdev.lifeassistant.entity.ITNews;
import cn.com.dyhdev.lifeassistant.entity.SubMessageText;
import cn.com.dyhdev.lifeassistant.entity.SubNewsMessage;
import cn.com.dyhdev.lifeassistant.entity.Version;

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

    /**
     * 解析手机号归属地Json数据
     * @param string
     * @return
     */
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


    /**
     * 解析IT资讯Json数据
     * @param string
     * @return
     */
    public static List<ITNews> parsingITNewsJson(String string){
        List<ITNews> tempList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(string);
            JSONArray jsonArray = jsonObject.getJSONArray("newslist");
            if(jsonArray != null){
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject json = (JSONObject)jsonArray.get(i);
                    ITNews itNews = new ITNews();
                    itNews.setTitle(json.getString("title"));
                    itNews.setTime(json.getString("ctime"));
                    itNews.setSource(json.getString("description"));
                    itNews.setPicUrl(json.getString("picUrl"));
                    itNews.setUrl(json.getString("url"));
                    tempList.add(itNews);
                }
                return tempList;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static List<Beauty> parsingBeautyDataJson(String string){
        List<Beauty> tempList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(string);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            if(jsonArray != null){
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject json = (JSONObject)jsonArray.get(i);
                    Beauty beauty = new Beauty();
                    beauty.setUrl(json.getString("url"));
                    tempList.add(beauty);
                }
                return tempList;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析版本更新Json数据
     * @param string
     * @return
     */
    public static Version parsingVersionJson(String string){
        Version version = new Version();
        try {
            JSONObject jsonObject = new JSONObject(string);
            version.setVersionName(jsonObject.getString("versionName"));
            version.setVersionCode(jsonObject.getInt("versionCode"));
            version.setContent(jsonObject.getString("content"));
            version.setUrl(jsonObject.getString("url"));
            return version;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析语音聊天Json数据
     * @param string
     * @return
     */
    public static SubMessageText parsingChatMsgJson(String string){

        try {
            Log.d(TAG, "parsingChatMsgJson: " + string);
            JSONObject jsonObject = new JSONObject(string);
            String jsonText = jsonObject.getString("text");
            String jsonUrl = null;
            JSONArray jsonNewsList = null;
            Log.d(TAG, "parsingChatMsgJson: " + jsonText);
            if(string.contains("url") && !string.contains("list")){
                jsonUrl = jsonObject.getString("url");
            }else if(string.contains("list")){
                jsonNewsList = jsonObject.getJSONArray("list");
            }
            SubMessageText text = new SubMessageText();
            List<SubNewsMessage> list = new ArrayList<>();
            if(jsonText != null){
                text.setText(jsonText);
                if(jsonUrl != null){
                    text.setUrl(jsonUrl);
                }else if(jsonNewsList != null && jsonText.contains("新闻")){
                    for(int i = 0; i < jsonNewsList.length(); i++){
                        SubNewsMessage sm = new SubNewsMessage();
                        JSONObject object = (JSONObject)jsonNewsList.get(i);
                        sm.setArticle(object.getString("article"));
                        sm.setSource(object.getString("source"));
                        sm.setDetailurl(object.getString("detailurl"));
                        list.add(sm);
                    }
                    text.setList(list);
                }
                return text;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
