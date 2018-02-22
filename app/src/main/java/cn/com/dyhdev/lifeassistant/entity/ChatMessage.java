package cn.com.dyhdev.lifeassistant.entity;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.entity
 * 文件名:     ChatMessage
 * 作者:       dyh
 * 时间:       2018/2/21 19:01
 * 描述:       发送post请求实体类
 */

public class ChatMessage {
    private String key;
    private String info;
    private String userid;

    public ChatMessage(String key, String info, String userid) {
        this.key = key;
        this.info = info;
        this.userid = userid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
