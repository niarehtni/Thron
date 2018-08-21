package com.rd.zhongqipiaoetong.payment;

/**
 * Author: Hubert
 * E-mail: hbh@erongdu.com
 * Date: 16/8/12 下午8:31
 * <p/>
 * Description:
 */
public interface ToPaymentHandler {
    void check(int code, String value);
}
