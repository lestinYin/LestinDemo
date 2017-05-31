package com.yin.lestin.lestindemo.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.yin.lestin.lestindemo.R;
import com.yin.lestin.lestindemo.utils.image.ShowImage;

public class AShowImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ashow_image);
        ImageView iv = (ImageView) findViewById(R.id.iv_show);
        ShowImage.show(this,"https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1496224925&di=36c87d2e6cd097896ead6445e9b01a57&src=http://c.hiphotos.baidu.com/zhidao/pic/item/b21c8701a18b87d6c58daf0d050828381e30fdd1.jpg",iv);
    }
}
