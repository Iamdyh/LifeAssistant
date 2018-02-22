package cn.com.dyhdev.lifeassistant.entity;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.entity
 * 文件名:     ITNews
 * 作者:       dyh
 * 时间:       2018/2/22 17:23
 * 描述:       IT资讯实体类
 */

public class ITNews {
    private String title;
    private String time;
    private String source;
    private String picUrl;
    private String url;

    public ITNews(){

    }

    public ITNews(String title, String time, String source, String picUrl, String url) {
        this.title = title;
        this.time = time;
        this.source = source;
        this.picUrl = picUrl;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
