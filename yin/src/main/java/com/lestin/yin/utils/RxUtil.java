package com.lestin.yin.utils;




import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * autour: lestin.yin
 * email:lestin.yin@gmail.com
 * date: 2017/6/6 14:53
 * version:
 * description:
*/

public class RxUtil {
    private static Consumer<Throwable> defaultErrorHandler(Object object) {
        return throwable -> LogUtil.tag(object).e(throwable);
    }

    public static <T> Subscription execNewObserveOnMain(
            Observable<T> observable,
            Consumer<T> callback) {

        return (Subscription) observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(callback, defaultErrorHandler(null));
    }

    public static <T> Observable<T> network(Observable<T> networkRequest) {
        return networkRequest.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @SuppressWarnings("UnusedParameters")
    public static void nothing(Object... ignoredArgs) {
    }
}
