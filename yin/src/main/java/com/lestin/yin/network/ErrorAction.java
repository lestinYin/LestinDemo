package com.lestin.yin.network;


public interface ErrorAction<Error extends Throwable> {
    void handle(Error throwable);
}