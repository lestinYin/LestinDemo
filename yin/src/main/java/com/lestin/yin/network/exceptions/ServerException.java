package com.lestin.yin.network.exceptions;

/**
 * autour: lestin.yin
 * email:lestin.yin@gmail.com
 * date: 2017/6/21 16:22
 * version:
 * description:
*/
public class ServerException extends IllegalStateException {

    public int status;

    public ServerException(int status) {
        super(String.format("服务器错误: %s", status));
        this.status = status;
    }
}
