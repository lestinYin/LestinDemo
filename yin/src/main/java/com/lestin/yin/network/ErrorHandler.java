package com.lestin.yin.network;

/** 
 * autour: lestin.yin
 * email:lestin.yin@gmail.com
 * date: 2017/6/6 16:40
 * version: 
 * description: 
*/

public class ErrorHandler <Error extends Throwable>{
    private Class<Error> mClass;
    private ErrorAction<Error> mHandler;

    private ErrorHandler(Class<Error> aClass, ErrorAction<Error> handler) {
        mClass = aClass;
        mHandler = handler;
    }

    public static <T extends Throwable> ErrorHandler<T> create(Class<T> cls, ErrorAction<T> handler) {
        return new ErrorHandler<>(cls, handler);
    }

    public boolean handle(Throwable throwable) {
        if (mClass.isInstance(throwable)) {
            //noinspection unchecked
            mHandler.handle((Error) throwable);
            return true;
        }
        return false;
    }

    public Class<Error> getErrorClass() {
        return mClass;
    }
}
