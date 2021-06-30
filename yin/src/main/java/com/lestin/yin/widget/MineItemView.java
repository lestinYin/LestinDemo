package com.lestin.yin.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lestin.yin.R;


public class MineItemView extends RelativeLayout {
    private Context context;
    private String textName;
    private boolean showLine;

    private ImageView headImg;
    private TextView mName;
    private TextView mRightText;
    private String rightText;
    private int img;
    private View line;


    public MineItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        initLayout(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {

            TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MainItem, 0, 0);
            this.img = typeArray
                    .getResourceId(R.styleable.MainItem_img,
                            attrs.getAttributeResourceValue(null, "src", 0));
            textName = typeArray.getString(R.styleable.MainItem_name);
            showLine = typeArray.getBoolean(R.styleable.MainItem_line, true);
            rightText = typeArray.getString(R.styleable.MainItem_rightName);
            this.context = context;
            typeArray.recycle();
        }
    }

    private void initLayout(Context context, AttributeSet attrs) {
        LayoutInflater.from(context)
                .inflate(R.layout.item_mine, this);
        this.headImg = findViewById(R.id.iv_img);
        this.mName = findViewById(R.id.tv_name);
        this.line = findViewById(R.id.line);
        this.mRightText = findViewById(R.id.tv_right);

        mName.setText(textName);
        mRightText.setText(rightText);
        if (!showLine) line.setVisibility(GONE);

        headImg.setBackgroundResource(img);

    }

    public TextView getRightText() {
        return mRightText;
    }

    public TextView getText() {
        return mName;
    }
}
