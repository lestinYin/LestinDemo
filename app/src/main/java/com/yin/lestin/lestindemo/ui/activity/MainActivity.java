package com.yin.lestin.lestindemo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pensees.secret.JNITest;
import com.yin.lestin.lestindemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lestin
 */
public class MainActivity extends AppCompatActivity {
    /**合并*/
    private List<String> mDatas;
    private View view;
    private RecyclerView lv;
    private RelativeLayout mBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initLv();

        int i = JNITest.secreateIsOk();
        Log.e("----",String.valueOf(i));
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    private void initLv() {
        lv = (RecyclerView) findViewById(R.id.lv);
        mBottom =  findViewById(R.id.rl_bottom);
        view = findViewById(R.id.view);
        LvAdapter lvAdapter = new LvAdapter(R.layout.simple_item, mDatas);
        lv.setAdapter(lvAdapter);




    }

    public class LvAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

        public LvAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
            setOnItemClickListener(this);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, String item) {
            helper.setText(R.id.id_num, item);

        }

        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            switch (position) {
                case 0:
                    startActivity(new Intent(getApplicationContext(), AShowImage.class));
//                    startActivity(new Intent(getApplicationContext(), AWebView.class).putExtra("url", "http://tyhqadcw.qdingnet.com/qdp-data-center-webui/#/data-center/dashboard"));
                    break;
                case 1:
                    startActivity(new Intent(getApplicationContext(), ARequestNet.class));
                    break;
                case 2:
                    startActivity(new Intent(getApplicationContext(), AWebView.class).putExtra("url", "https://mp.weixin.qq.com/s?__biz=MzA5MzI3NjE2MA==&mid=2650248230&idx=1&sn=b1ed5b205fcd2745e040a2b14d4c2a0f&chksm=88636549bf14ec5f54530fa2c2bd3c4c44fab4512126230aff8f46f17679ed36d6e44ff08a60&scene=27#wechat_redirect"));
//                    startActivity(new Intent(getApplicationContext(), AWebView.class).putExtra("url", "http://tyhqadcw.qdingnet.com/qdp-data-center-webui/#/data-center/dashboard"));
                    break;
                case 3:
                    startActivity(new Intent(getApplicationContext(), AWechat.class));
                    break;
                case 4:
                    startActivity(new Intent(getApplicationContext(), AIm.class));
                    break;
                default:
                    break;
            }
        }
    }


    protected void initData() {
        mDatas = new ArrayList();
        mDatas.add("显示图片");
        mDatas.add("网络请求");
        mDatas.add("webView");
        mDatas.add("微信公众号");
        mDatas.add("WebJs");
    }

}
