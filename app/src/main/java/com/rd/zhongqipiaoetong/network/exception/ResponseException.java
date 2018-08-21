package com.rd.zhongqipiaoetong.network.exception;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/25 20:47
 * <p/>
 * Description: 网络Response异常
 */
public class ResponseException extends RuntimeException {
    public ResponseException(String detailMessage) {
        super(detailMessage);
    }

    public ResponseException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ResponseException(Throwable throwable) {
        super(throwable);
    }
}