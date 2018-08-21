package com.rd.zhongqipiaoetong.module.account.bond;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/30/16.
 */
public class BondUtils {
    /**
     * 实际到账金额（接承金额）
     *
     * @param money
     *         受让金额
     * @param apr
     *         折让率
     * @param interest
     *         持有利息
     * @param interestFee
     *         承接金额
     *
     * @return
     */
    public static double creditDealAmt(double money, double apr, double interest, double interestFee) {
        return money - money * apr + interest - interestFee;
    }

    /**
     * 手续费费率
     *
     * @param sellFee
     *         手续费费率
     * @param tppMaxFeeRate
     *         最大手续费费率
     * @param type
     *         收款方式：1.固定;2.比例
     * @param money
     *         受让金额
     * @param apr
     *         折让率
     * @param interest
     *         持有利息
     * @param interestFee
     *         承接金额
     *
     * @return
     */
    public static double sellFee(double sellFee, double tppMaxFeeRate, int type, double money, double apr, double interest, double interestFee) {
        if (tppMaxFeeRate == 0) {//若tppMaxFeeRate为0，则不收取手续费
            return 0;
        }
        if (type == 1) {//若feeType为1时，sellFee  >   creditDealAmt * tppMaxFeeRate，不收取手续费；不然就收取手续费sellFee
            return sellFee > creditDealAmt(money, apr, interest, interestFee) * tppMaxFeeRate ? 0 : sellFee;
        } else if (type == 2) {//若feeType为2时，tppMaxFeeRate < sellFee, 则sellFee = tppMaxFeeRate，手续费：creditDealAmt * sellFee
            return tppMaxFeeRate < sellFee ? tppMaxFeeRate : sellFee;
        }
        return 0;
    }

    /**
     * 手续费
     *
     * @param sellFee
     *         手续费费率
     * @param tppMaxFeeRate
     *         最大手续费费率
     * @param type
     *         收款方式：1.固定;2.比例
     * @param money
     *         受让金额
     * @param apr
     *         折让率
     * @param interest
     *         持有利息
     * @param interestFee
     *         承接金额
     *
     * @return
     */
    public static double fee(double sellFee, double tppMaxFeeRate, int type, double money, double apr, double interest, double interestFee) {
        return creditDealAmt(money, apr, interest, interestFee) * sellFee(sellFee, tppMaxFeeRate, type, money, apr, interest, interestFee);
    }
}
