package cn.com.dyhdev.lifeassistant.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.dyhdev.lifeassistant.R;
import cn.com.dyhdev.lifeassistant.adapter.CourierAdapter;
import cn.com.dyhdev.lifeassistant.entity.CourierDean;
import cn.com.dyhdev.lifeassistant.retrofit.RetrofitUtils;
import cn.com.dyhdev.lifeassistant.utils.JsonUtils;
import cn.com.dyhdev.lifeassistant.utils.StaticClass;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpressActivity extends BaseActivity {

    private static final String TAG = "ExpressActivity";

    @BindView(R.id.id_et_company)
    EditText mEtCompany;
    @BindView(R.id.id_et_order_id)
    EditText mEtOrderId;
    @BindView(R.id.id_btn_express_search)
    Button mBtnExpressSearch;
    @BindView(R.id.id_recyclerview)
    RecyclerView mRecyclerview;

    private List<CourierDean> mList = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.id_btn_express_search)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.id_btn_express_search:
                getData();

                break;
        }
    }

    private void setRecyclerView(List<CourierDean> list){
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setAdapter(new CourierAdapter(mList));
//        mRecyclerview.setItemAnimator(new DefaultItemAnimator());

    }

    /**
     * 获取数据
     */
    private void getData() {
        String name = mEtCompany.getText().toString().trim();
        String orderId = mEtOrderId.getText().toString().trim();
        if(!TextUtils.isEmpty(name) && ! TextUtils.isEmpty(orderId)){

            queryExpress(name, orderId);

        }else{
            Toast.makeText(this, R.string.et_remind, Toast.LENGTH_SHORT).show();
        }
    }

    private void queryExpress(String name, String orderId) {
        //快递查询接口：http://v.juhe.cn/exp/index?key=key&com=sf&no=575677355677
        Log.d(TAG, "queryExpress: " + name + " : " + orderId);
        //String url = StaticClass.EXPRESS_URL + StaticClass.EXPRESS_URL + "&com=" + name + "&no=" + orderId;
        //RetrofitUtils.doGetRequest(StaticClass.EXPRESS_URL, StaticClass.EXPRESS_APPKEY, name, orderId);
        //String t = HttpUtils.doCourierGet(url);

        RetrofitUtils.doCourierGetRequest(StaticClass.EXPRESS_URL, StaticClass.EXPRESS_APPKEY, name, orderId, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    final String res = response.body().string();

                    mList = JsonUtils.parsingCourierJson(res);
                    Log.d(TAG, "queryExpress: " + res);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(mList != null){
                                //倒序
                                Collections.reverse(mList);
                                setRecyclerView(mList);
                            }else{
                                Toast.makeText(ExpressActivity.this, R.string.search_failed, Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ExpressActivity.this, R.string.search_failed, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }




}
