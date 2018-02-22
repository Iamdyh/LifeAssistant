package cn.com.dyhdev.lifeassistant.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.dyhdev.lifeassistant.R;
import cn.com.dyhdev.lifeassistant.retrofit.RetrofitUtils;
import cn.com.dyhdev.lifeassistant.utils.JsonUtils;
import cn.com.dyhdev.lifeassistant.utils.StaticClass;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneActivity extends BaseActivity {

    private static final String TAG = "PhoneActivity";

    @BindView(R.id.id_et_phone)
    EditText mEtPhone;
    @BindView(R.id.id_btn_express_search)
    Button mBtnExpressSearch;
    @BindView(R.id.id_img_company)
    ImageView mImgCompany;
    @BindView(R.id.id_tv_phone_content)
    TextView mTvPhoneContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.id_btn_express_search)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_btn_express_search:
                mTvPhoneContent.setText("");
                Log.d(TAG, "onViewClicked: " + mTvPhoneContent.getText().toString());
                mImgCompany.setVisibility(View.GONE);
                getData();
                break;

        }
    }

    /**
     * 获取数据
     */
    private void getData() {
        String phone = mEtPhone.getText().toString().trim();
        if(!TextUtils.isEmpty(phone)){
            queryPhone(phone);
        }else{
            Toast.makeText(this, R.string.et_remind, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 查询手机号归属地
     * @param phone
     */
    private void queryPhone(String phone) {
        RetrofitUtils.doPhoneGetRequest(StaticClass.PHONE_URL, StaticClass.PHONE_APPKEY, phone, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    final String string = response.body().string();

                    Log.d(TAG, "onResponse: " + string);
                    final String[] res = JsonUtils.parsingPhoneJson(string);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(res != null)
                                showPhoneInfo(res);
                            else{
                                Toast.makeText(PhoneActivity.this, R.string.search_failed, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(PhoneActivity.this, R.string.search_failed, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 展示手机号信息
     * @param strings
     */
    private void showPhoneInfo(String[] strings){
        mTvPhoneContent.append("归属地：" + strings[0] + strings[1] + "\n");
        mTvPhoneContent.append("区号：" + strings[2] + "\n");
        mTvPhoneContent.append("邮编：" + strings[3] + "\n");
        mTvPhoneContent.append("类型：" + strings[4]);

        //图片显示
        switch (strings[4]){
            case "移动":
                mImgCompany.setVisibility(View.VISIBLE);
                mImgCompany.setBackgroundResource(R.drawable.china_mobile);
                break;
            case "联通":
                mImgCompany.setVisibility(View.VISIBLE);
                mImgCompany.setBackgroundResource(R.drawable.china_unicom);
                break;
            case "电信":
                mImgCompany.setVisibility(View.VISIBLE);
                mImgCompany.setBackgroundResource(R.drawable.china_telecom);
                break;
        }
    }
}
