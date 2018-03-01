package cn.com.dyhdev.lifeassistant.entity;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.entity
 * 文件名:     Version
 * 作者:       dyh
 * 时间:       2018/3/1 15:10
 * 描述:       版本更新的实体类
 */

public class Version {

    private String versionName;
    private int versionCode;
    private String content;
    private String url;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
