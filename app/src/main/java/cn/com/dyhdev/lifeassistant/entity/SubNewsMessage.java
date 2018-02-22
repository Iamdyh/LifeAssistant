package cn.com.dyhdev.lifeassistant.entity;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.entity
 * 文件名:     SubNewsMessage
 * 作者:       dyh
 * 时间:       2018/2/22 14:04
 * 描述:       服务器返回的新闻信息
 */

public class SubNewsMessage {

    private String article;
    private String source;
    private String detailurl;

    public SubNewsMessage(){

    }

    public SubNewsMessage(String article, String source, String detailurl) {
        this.article = article;
        this.source = source;
        this.detailurl = detailurl;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDetailurl() {
        return detailurl;
    }

    public void setDetailurl(String detailurl) {
        this.detailurl = detailurl;
    }
}
