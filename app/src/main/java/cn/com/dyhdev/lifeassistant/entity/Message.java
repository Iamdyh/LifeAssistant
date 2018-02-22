package cn.com.dyhdev.lifeassistant.entity;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.entity
 * 文件名:     Message
 * 作者:       dyh
 * 时间:       2018/2/21 16:23
 * 描述:       获取消息实体类
 */

public class Message {

    private String mContent;
    private int type;

    public Message(){

    }


    public Message(String content, int t){
        this.mContent = content;
        this.type = t;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }



}
