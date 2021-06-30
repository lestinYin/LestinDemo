package com.lestin.yin.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.lestin.yin.R;

import java.util.List;

public class CommonPagerAdapter<T extends CharSequence> extends FragmentStatePagerAdapter {
    private List<T> mTitleList;
    private List<Fragment> mFragmentList;
    private Context context;

    public CommonPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<T> titleList) {
        super(fm);
        this.mFragmentList = fragmentList;
        this.mTitleList = titleList;
    }

    public CommonPagerAdapter(Context context, FragmentManager fm, List<Fragment> fragmentList, List<T> titleList) {
        super(fm);
        this.mFragmentList = fragmentList;
        this.mTitleList = titleList;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (null == mTitleList || position >= mTitleList.size()) {
            return super.getPageTitle(position);
        }
        return mTitleList.get(position);

    }

    //如果不希望切换ViewPager的时候每次都重新创建Fragment的话，也很简单只需要将适配器的两个方法注释掉就好：
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }
    @Override
    public int getItemPosition(Object object) {
        // TODO Auto-generated method stub
        return PagerAdapter.POSITION_NONE;
    }


    public View getTabView(String title, String msg, boolean showRed) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tabview, null);
        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setText(title);
//        TextView img = (TextView) view.findViewById(R.id.imageView);
//        if (showRed) {
//            img.setVisibility(View.VISIBLE);
//            img.setText(msg);
//        } else {
//            img.setVisibility(View.GONE);
//        }
        return view;
    }
}
