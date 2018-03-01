package cn.com.dyhdev.lifeassistant.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.dyhdev.lifeassistant.R;
import cn.com.dyhdev.lifeassistant.retrofit.ProgressListener;
import cn.com.dyhdev.lifeassistant.retrofit.RetrofitUtils;
import cn.com.dyhdev.lifeassistant.utils.FileUtils;
import cn.com.dyhdev.lifeassistant.utils.NetworkUtils;
import cn.com.dyhdev.lifeassistant.utils.StaticClass;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateActivity extends BaseActivity {

    private static final String TAG = "UpdateActivity";

    @BindView(R.id.id_update_size_tv)
    TextView mUpdateSizeTv;
    @BindView(R.id.id_number_progress_bar)
    NumberProgressBar mNumberProgressBar;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);
        mNumberProgressBar.setMax(100);
        getUrl();
    }

    private void getUrl() {

        url = getIntent().getStringExtra("url");
        Log.d(TAG, "getUrl: " + url);
        if (!TextUtils.isEmpty(url)) {
            //下载
            startDownload(url);
        }
    }

    private void startDownload(String url) {
        RetrofitUtils.doDownloadApkGetRequest(StaticClass.VERSION_URL, url, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                Log.d(TAG, "onResponse: " + response.body().contentLength() + " : " + response.body().contentType());

                final File file = FileUtils.createFile(UpdateActivity.this);
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        //保存文件到本地
                        FileUtils.writeFile2Disk(response, file, new ProgressListener() {
                            @Override
                            public void onProgress(final long progress, final long total, final boolean done) {
                                //更新进度条
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        mUpdateSizeTv.setText(progress + " / " + total);
                                        mNumberProgressBar.setProgress((int)(((float)progress / (float)total) * 100));

                                        if (done) {
                                            mUpdateSizeTv.setText("下载完成！");
                                            //startInstallApk();
                                        } else if (!done && NetworkUtils.getNetworkState(UpdateActivity.this) == NetworkUtils.NETWORK_NONE) {
                                            mUpdateSizeTv.setText("下载失败！");
                                        }

                                    }
                                });
                            }
                        });
                    }
                }.start();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * 启动安装
     */
    private void startInstallApk() {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/update.apk")), "application/vnd.android.package-archive");
        startActivity(i);
        //finish();
    }


    @OnClick(R.id.id_update_size_tv)
    public void onViewClicked() {
    }
}
