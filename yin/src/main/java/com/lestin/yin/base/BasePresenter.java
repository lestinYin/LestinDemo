package com.lestin.yin.base;



import com.lestin.yin.network.ApiManager;
import com.lestin.yin.network.BaseApi;
import com.lestin.yin.network.RequestHandler;
import com.lestin.yin.utils.RxUtil;

import java.lang.ref.SoftReference;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;


/**
 * Created by prin on 2016/4/6.
 *
 * 业务逻辑处理类基类
 */
public abstract class BasePresenter<V extends IBaseView>  {

    private SoftReference<V> mView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    /**
     * 将Presenter与View层绑定
     */
    void attachView(V view) {
        this.mView = new SoftReference<>(view);
    }

    /**
     * 将Presenter于View层解绑
     */
    void detachView() {
        if (mView != null) {
            this.mView.clear();
        }
        this.mView = null;
        compositeDisposable.dispose();
    }

    /**
     * 获取具体的View层对象
     */
    public V getRealView() {
        return mView != null ? mView.get() : null;
    }

    protected boolean isDetach() {
        return mView == null || mView.get() == null;
    }

    /*
     * Retrofit & RxJava
     */


//    @Override
//    public <T> void execCatchError(Observable<T> request,
//                                   Action1<T> resultCallback,
//                                   Func1<Throwable, Boolean> errorCallback) {
//
//        mSubscriptions.add(RxUtil.network(request).subscribe(resultCallback, throwable -> {
//            if (errorCallback.call(throwable)) {
//                return;
//            }
//            if (RequestHandler.handleError(throwable)) {
//                return;
//            }
//            L.e(throwable, "未拦截的错误");
//        }));
//    }
//
//    @Override
//    public <T> void execNetworkError(Observable<T> request,
//                                     Action1<T> resultCallback,
//                                     Action1<Throwable> errorCallback,
//                                     Action1<IOException> networkErrorCallback) {
//
//        mSubscriptions.add(RxUtil.network(request).subscribe(resultCallback, throwable -> {
//            // 回调网络错误
//            if (RequestHandler.handleError(throwable, ErrorHandler.create(
//                    IOException.class,
//                    networkErrorCallback::call
//            ))) {
//                return;
//            }
//            errorCallback.call(throwable);
//        }));
//    }

    public <T> void exec(Observable<T> request, Consumer<T> resultCallback) {
        exec(request, resultCallback, RequestHandler::handleError);
    }

    /**
     * 默认的请求处理拦截器优先，同时在没有处理错误时交给自己的错误处理器处理错误
     * <p>
     * Run a common retrofit request
     */
    public <T> void exec(Observable<T> request, Consumer<T> resultCallback, Consumer<Throwable> errorCallback) {
//        showLoadingDialog();
        compositeDisposable.add(RxUtil.network(request).subscribe(t -> {
//            hideLoadingDialog();
            resultCallback.accept(t);
        }, throwable -> {
//            hideLoadingDialog();
            if (RequestHandler.handleError(throwable)) {
                errorCallback.accept(throwable);
                return;
            }
            errorCallback.accept(throwable);
        }));
    }

    public BaseApi baseApi() {
        return ApiManager.getBaseApi();
    }

}
