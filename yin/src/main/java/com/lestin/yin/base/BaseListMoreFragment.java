package com.lestin.yin.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;


import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lestin.yin.MyApplication;
import com.lestin.yin.R;
import com.lestin.yin.network.RequestHandler;
import com.lestin.yin.utils.DeviceUtil;
import com.lestin.yin.widget.statelayout.PrimaryStateLayout;
import com.lestin.yin.widget.statelayout.ShowStateOption;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author lestin.yin yinmaolin8@gmail.com
 * @name PreDiagnosis
 * @class name：com.doctorai.prediagnosis.base
 * @class describe
 * @time 2017/12/18 下午5:57
 * @change
 * @chang time
 * @class describe 下拉刷新上拉加载数据基类
 */
public abstract class BaseListMoreFragment<E extends DataMode<T>, T> extends BaseFragment {
    public CompositeDisposable compositeDisposable = new CompositeDisposable();
    PrimaryStateLayout state;
    SwipeRefreshLayout swipeRefresh;
    RecyclerView mRecyclerView;
    public int mPage = 1;
    AdapterInterface<T> mAdapter;


    @Override
    public int layoutId() {
        return R.layout.list_common;
    }

    @Override
    public void start() {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        state = view.findViewById(R.id.stateFullLayouts);
        swipeRefresh = view.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = view.findViewById(R.id.recylerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getContext()));
        mAdapter = getAdapter();

        if (mAdapter != null && mAdapter instanceof RecyclerView.Adapter) {
            mRecyclerView.setAdapter((RecyclerView.Adapter) mAdapter);
            swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mPage = 1;
                    loadData(mPage);
                }
            });
        }

        mRecyclerView.setAdapter((RecyclerView.Adapter) mAdapter);
        // 设置加载更多监听
//        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
//            @Override
//            public void onLoadMore() {
//                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);
//                mPage += 1;
//                loadData(mPage);
//            }
//        });
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setRefreshing(false);
//        loadData(mPage);
//        state.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                mPage = 1;
//                loadData(mPage);
//                return true;
//            }
//        });
    }



    @Override
    public void onResume() {
        super.onResume();
        mPage = 1;
        loadData(mPage);

    }

    public void loadData(int page) {
//        state.showCustom(new ShowStateOption("正在加载...", R.drawable.no_data_circle));
        showProgressDialog();
        boolean isNet = DeviceUtil.isNetworkAvailable(getActivity());
        if (!isNet) {
            closeProgressDialog();
            state.showCustom(new ShowStateOption("开小差\n不小心出错了没网", R.drawable.no_data_circle));
            return;
        }
        Observable<E> observable = getData(page);
        if (observable == null) return;
        compositeDisposable.add(observable
                .doOnError(throwable -> {
                    if (mPage > 1) mPage--;
                    closeProgressDialog();
                    state.showCustom(new ShowStateOption("开小差\n不小心出错了\n访问不成功", R.drawable.no_data_circle));
                })
                .onExceptionResumeNext(Observable.<E>empty())
                .onErrorResumeNext(Observable.<E>empty())
                .subscribe(dataMode -> {
                    state.showContent();
                    String resultMessage = dataMode.getResultMessage();
                    if (dataMode != null && dataMode.getList() != null && TextUtils.isEmpty(resultMessage)) {
                        if (mPage == 1) {
                            swipeRefresh.setRefreshing(false);
                            if (dataMode.getList().size() <= 0) {
                                state.showCustom(new ShowStateOption("无相关内容", R.drawable.no_data_circle));
                            } else {
                                mAdapter.setData(dataMode.getList());
//                                loadMoreWrapper.notifyDataSetChanged();
                            }
                        } else {
                            if (dataMode.getList().size() == 0) {
//                                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                            } else {
//                                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
                                mAdapter.addData(dataMode.getList());
//                                loadMoreWrapper.notifyDataSetChanged();
                            }
                        }
                    } else {
                        state.showCustom(new ShowStateOption("无相关内容", R.drawable.no_data_circle));
                        closeProgressDialog();
                    }
                    closeProgressDialog();
                }, throwable -> {
                    RequestHandler.handleError(throwable);
                }));

    }



    protected abstract AdapterInterface<T> getAdapter();

    protected abstract Observable<E> getData(int page);


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}

