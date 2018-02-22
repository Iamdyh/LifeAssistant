package cn.com.dyhdev.lifeassistant.entity;

import java.util.List;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.entity
 * 文件名:     SubMessageText
 * 作者:       dyh
 * 时间:       2018/2/22 13:59
 * 描述:       服务器返回的普通数据
 */

public class SubMessageText {
    private String text;
    private String url;
    private List<SubNewsMessage> list;

    public SubMessageText(){

    }

    public SubMessageText(String text) {
        this.text = text;
    }

    public SubMessageText(String text, String url) {
        this.text = text;
        this.url = url;
    }

    public SubMessageText(String text, List<SubNewsMessage> list) {
        this.text = text;
        this.list = list;
    }

    public SubMessageText(String text, String url, List<SubNewsMessage> list) {
        this.text = text;
        this.url = url;
        this.list = list;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<SubNewsMessage> getList() {
        return list;
    }

    public void setList(List<SubNewsMessage> list) {
        this.list = list;
    }
}
