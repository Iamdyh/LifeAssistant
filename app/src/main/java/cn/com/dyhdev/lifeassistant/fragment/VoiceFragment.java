package cn.com.dyhdev.lifeassistant.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.dyhdev.lifeassistant.R;
import cn.com.dyhdev.lifeassistant.adapter.VoiceAdapter;
import cn.com.dyhdev.lifeassistant.entity.ChatMessage;
import cn.com.dyhdev.lifeassistant.entity.Message;
import cn.com.dyhdev.lifeassistant.entity.SubMessageText;
import cn.com.dyhdev.lifeassistant.retrofit.RetrofitUtils;
import cn.com.dyhdev.lifeassistant.utils.JsonUtils;
import cn.com.dyhdev.lifeassistant.utils.SharedUtils;
import cn.com.dyhdev.lifeassistant.utils.StaticClass;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.fragment
 * 文件名:     VoiceFragment
 * 作者:       dyh
 * 时间:       2018/1/6 14:30
 * 描述:       语音助手
 */

public class VoiceFragment extends Fragment {

    private static final String TAG = "VoiceFragment";
    @BindView(R.id.id_voice_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.id_voice_btn_send)
    Button mBtnSend;
    @BindView(R.id.id_voice_et_input)
    EditText mEtInput;
    Unbinder unbinder;

    private List<Message> mList = new ArrayList<>();
    private VoiceAdapter mAdapter;

    private SpeechSynthesizer mTts;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voice, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @OnClick(R.id.id_voice_btn_send)
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.id_voice_btn_send:
                getData();
                break;
        }
    }

    private void initView(){

        mTts = SpeechSynthesizer.createSynthesizer(getActivity(), null);
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
        mTts.setParameter(SpeechConstant.SPEED, "50");
        mTts.setParameter(SpeechConstant.VOLUME, "50");
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端


        mAdapter = new VoiceAdapter(mList, getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        addLeftMsg(getString(R.string.voice_name));
    }

    /**
     * 获取数据
     */
    private void getData() {
        String text = mEtInput.getText().toString().trim();
        addRightMsg(text);
        sendMessage(text);
    }

    /**
     * 发送消息
     * @param text
     */
    private void sendMessage(String text) {
        ChatMessage info = new ChatMessage(StaticClass.ROBOT_APPKEY, text, SharedUtils.getString(getActivity(), StaticClass.USERNAME_KEY, StaticClass.DEFAULT_VALUE));
        Gson gson = new Gson();
        String json = gson.toJson(info);   //利用Gson将对象转换成Json字符串
        Log.d(TAG, "sendMessage: " + json);

        RetrofitUtils.doChatPostRequest(StaticClass.ROBOT_URL, json, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    //response.body().string():只能调用一次
                    //Log.d(TAG, "onResponse: " + response.body().string());
                    final String res = response.body().string();
                    Log.d(TAG, "onResponse: " + res);
                    final SubMessageText subText = JsonUtils.parsingChatMsgJson(res);
                    Log.d(TAG, "onResponse2: " + subText.getText());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(subText != null){
                                addLeftMsg(subText.getText());
                                if(subText.getUrl() != null){
                                    addLeftMsg(subText.getUrl());
                                }else if(subText.getList() != null){
                                    for(int i = 0; i < 2; i++){
                                        String string = "标题：" + subText.getList().get(i).getArticle() + "\n" +
                                                        "来源：" + subText.getList().get(i).getSource() + "\n" +
                                                        "详细内容：" + subText.getList().get(i).getDetailurl();
                                        addLeftMsg(string);

                                    }
                                }
                            }
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    /**
     * 左边的消息
     * @param text
     */
    private void addLeftMsg(String text){

        //判断语音模式是否打开
        boolean isSpeak = SharedUtils.getBoolean(getActivity(), "isSpeak", false);
        if(isSpeak){
            startSpeak(text);

        }
        Message message = new Message();
        message.setType(VoiceAdapter.TYPE_RECEIVED);
        message.setmContent(text);
        mAdapter.addData(message);
        mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);

    }

    /**
     * 右边的消息
     * @param text
     */
    private void addRightMsg(String text){
        Message message = new Message();
        message.setType(VoiceAdapter.TYPE_SEND);
        message.setmContent(text);
        mAdapter.addData(message);
        mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
    }

    private void startSpeak(String string){
        mTts.startSpeaking(string, mSynListener);
    }

    private SynthesizerListener mSynListener = new SynthesizerListener() {
        @Override
        public void onSpeakBegin() {

        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }

        @Override
        public void onCompleted(SpeechError speechError) {

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
