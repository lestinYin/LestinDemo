package com.lestin.yin.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lestin.yin.MyApplication;
import com.lestin.yin.R;
import com.lestin.yin.network.RequestHandler;
import com.lestin.yin.utils.DeviceUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

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
public abstract class BaseCommonListFragment<E extends DataMode<T>, T> extends BaseFragment {
    public CompositeDisposable compositeDisposable = new CompositeDisposable();
    SmartRefreshLayout smartRefreshLayout;
    public RecyclerView mRecyclerView;
    public int mPage = 1;
    AdapterInterface<T> mAdapter ;


    @Override
    public int layoutId() {
        return R.layout.list_common_new;
    }

    @Override
    public void start() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recylerView);
        smartRefreshLayout = view.findViewById(R.id.smartRefreshLayout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getContext()));
        mAdapter = getAdapter();

        if (mAdapter != null && mAdapter instanceof RecyclerView.Adapter) {
            mRecyclerView.setAdapter((RecyclerView.Adapter) mAdapter);
        }

        mRecyclerView.setAdapter((RecyclerView.Adapter) mAdapter);

        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPage = 1;
            loadData(mPage);
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage = mPage + 1;
                loadData(mPage);
            }
        });

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
            return;
        }
        Observable<E> observable = getData(page);
        if (observable == null) return;
        compositeDisposable.add(observable
                .doOnError(throwable -> {
                    if (mPage > 1) mPage--;
                    closeProgressDialog();

                   mAdapter.noData();
                })
                .onExceptionResumeNext(Observable.<E>empty())
                .onErrorResumeNext(Observable.<E>empty())
                .subscribe(dataMode -> {
                    String resultMessage = dataMode.getResultMessage();
                    if (dataMode != null && dataMode.getList() != null && TextUtils.isEmpty(resultMessage)) {
                        if (mPage == 1) {
                            smartRefreshLayout.finishRefresh();
                            if (dataMode.getList().size() <= 0) {
                                mAdapter.noData();
                            } else {
                                mAdapter.setData(dataMode.getList());
                            }
                        } else {
                            smartRefreshLayout.finishLoadMore();      //加载完成
                            if (dataMode.getList().size() == 0) {
                                smartRefreshLayout.finishLoadMoreWithNoMoreData();  //全部加载完成,没有数据了调用此方法
                            } else {
                                mAdapter.addData(dataMode.getList());
                            }
                        }
                    } else {
                        mAdapter.noData();
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

