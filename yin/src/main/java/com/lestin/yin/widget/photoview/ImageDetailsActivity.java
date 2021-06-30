package com.lestin.yin.widget.photoview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.lestin.yin.R;
import com.lestin.yin.utils.image.ShowImage;

import java.util.ArrayList;


/**
 * 查看大图的Activity界面
 */

public class ImageDetailsActivity extends FragmentActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    public static final int IMG_REQ_CODE = 200;

    public final static String DELETED_IMGS = "deleted_imgs";
    public final static String REMAIN_IMGS = "remain_imgs";

    private HackyViewPager mViewPager;
    private View mDeleteView;
    private TextView mPageText;

    private ArrayList<String> mListImage;

    private ArrayList<String> mListDeletedPhotos = new ArrayList<>();

    private ViewPagerAdapter mAdapter;
    private String mHeadUrl = null;
    int mCurtPosition;
    boolean isDelete = false;
    boolean mUserDelete = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ashow_image);
        mCurtPosition = getIntent().getIntExtra("position", 0);
        isDelete = getIntent().getBooleanExtra("isDelete", false);
        mHeadUrl = getIntent().getStringExtra("headurl");
        mListImage = getIntent().getStringArrayListExtra("image_path");
        if (mListImage == null || mListImage.isEmpty()) {
            finish();
            return;
        }

        mPageText = (TextView) findViewById(R.id.action_title);
        mViewPager = (HackyViewPager) findViewById(R.id.view_pager);
        mAdapter = new ViewPagerAdapter();
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mCurtPosition);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setEnabled(false);
        mPageText.setText((mCurtPosition + 1) + "/" + mListImage.size());
        findViewById(R.id.btn_back).setOnClickListener(this);
        mDeleteView = findViewById(R.id.delete);
        if (isDelete) {
            mDeleteView.setVisibility(View.VISIBLE);
            mDeleteView.setOnClickListener(this);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    public static void start(int requestCode, Context context, ArrayList<String> imagePath, boolean isShowDelete, int curtPosition, String headUrl) {
//        if (requestCode == -1) {
        context.startActivity(new Intent(context, ImageDetailsActivity.class).putStringArrayListExtra("image_path", imagePath)
                .putExtra("isDelete", isShowDelete)
                .putExtra("position", curtPosition)
                .putExtra("headurl", headUrl));
//        } else {
//            context.startActivityForResult(new Intent(context, ImageDetailsActivity.class).putStringArrayListExtra("image_path", imagePath)
//                    .putExtra("isDelete", isShowDelete)
//                    .putExtra("position", curtPosition)
//                    .putExtra("headurl", headUrl), requestCode);
//        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_back) {
            getBack();
            return;
        }

        mUserDelete = true;
        mListImage.remove(mCurtPosition);
        mListDeletedPhotos.add(mListImage.get(mCurtPosition));

        mCurtPosition = (mCurtPosition == mListImage.size()) ? (mCurtPosition - 1) : mCurtPosition;
        mAdapter = new ViewPagerAdapter();
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mCurtPosition);
        mPageText.setText((mCurtPosition + 1) + "/" + mListImage.size());
        if (mListImage.isEmpty()) {
            Toast.makeText(this, "图片被删除完了", Toast.LENGTH_SHORT).show();
            getBack();
        }
    }

    private void getBack() {
        Intent intent = new Intent();
        intent.putStringArrayListExtra("resultList", mListImage);
        intent.putStringArrayListExtra(REMAIN_IMGS, mListImage);
        intent.putStringArrayListExtra(DELETED_IMGS, mListDeletedPhotos);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (mUserDelete && keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putStringArrayListExtra("resultList", mListImage);
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    class ViewHolder {
        PhotoView mImageView;

        public ViewHolder(View view) {
            mImageView = (PhotoView) view.findViewById(R.id.zoom_image_view);
        }

        public void build(String path) {
            mImageView.setZoomable(true);
            mImageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    finish();
                }
            });

            if (path.contains("http")) {
                ShowImage.showPhotoView(ImageDetailsActivity.this, path, mImageView);
            }
//            ImageLoader.getInstance().displayImage(path, mImageView, mDefaultOptions);
            else {
//                File file = new File(path);
//                ImageLoader.getInstance().displayImage(Uri.fromFile(file).toString(), mImageView);
            }
            //ImageLoader.getInstance().displayImage(path, mImageView, mDefaultOptions);
        }
    }

    /**
     * ViewPager的�?配器
     */
    class ViewPagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = getLayoutInflater().inflate(R.layout.image_details_item_layout, null);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.build(mListImage.get(position));
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return mListImage.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int currentPage) {
        mCurtPosition = currentPage;
        mPageText.setText((mCurtPosition + 1) + "/" + mListImage.size());
    }
}