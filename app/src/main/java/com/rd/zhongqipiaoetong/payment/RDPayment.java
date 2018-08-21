package com.rd.zhongqipiaoetong.payment;

import com.rd.zhongqipiaoetong.common.BaseParams;
import com.rd.zhongqipiaoetong.payment.china_pnr.PNRController;
import com.rd.zhongqipiaoetong.payment.moneymoremore.MoneyMoreMoreController;
import com.rd.zhongqipiaoetong.payment.umpay.UMPayController;

/**
 * Author: Hubert
 * E-mail: hbh@erongdu.com
 * Date: 16/8/12 下午4:37
 * <p/>
 * Description:
 */
public class RDPayment {
    private static final int PAYMENTTPYE_MOREMORE = 1;
    private static final int PAYMENTTPYE_PNR      = 2;
    private static final int PAYMENTTPYE_UFX      = 4;
    private static final int PAYMENTTYPE_UMPAY    = 3;
    /**
     * 调用单例对象
     */
    private static RDPayment     instance;
    private        PayController payController;

    private RDPayment() {
        payController = create();
    }

    public static RDPayment getInstance() {
        if (instance == null)
            instance = new RDPayment();
        return instance;
    }

    private PayController create() {
        switch (BaseParams.paymentType) {
            case PAYMENTTPYE_MOREMORE:
                return new MoneyMoreMoreController();
            case PAYMENTTPYE_PNR:
                return new PNRController();
            case PAYMENTTPYE_UFX:
                return new PNRController();
            case PAYMENTTYPE_UMPAY:
                return new UMPayController();
        }
        return null;
    }

    public PayController getPayController() {
        return payController;
    }

    public void setPayController(PayController payController) {
        this.payController = payController;
    }
}
