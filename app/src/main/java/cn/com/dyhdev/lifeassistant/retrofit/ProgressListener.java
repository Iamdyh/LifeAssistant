package cn.com.dyhdev.lifeassistant.retrofit;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.retrofit
 * 文件名:     ProgressListener
 * 作者:       dyh
 * 时间:       2018/3/1 16:15
 * 描述:       文件下载进度监听
 */

public interface ProgressListener {

    void onProgress(long progress, long total, boolean done);

}
