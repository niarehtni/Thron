package com.rd.zhongqipiaoetong.payment;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 6/12/16.
 */
public class ConverFormat {
    /**
     * 转换为 int，异常时返回0
     *
     * @param converObj
     *
     * @return
     */
    public static int toInt(Object converObj) {
        try {
            return Integer.valueOf(converObj.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 转换为 double，异常时返回0
     *
     * @param converObj
     *
     * @return
     */
    public static double toDouble(Object converObj) {
        try {
            return Double.valueOf(converObj.toString());
        } catch (Exception e) {
            return 0;
        }
    }


    /**
     * 转换为 long，异常时返回0
     *
     * @param converObj
     *
     * @return
     */
    public static long toLong(Object converObj) {
        try {
            return Long.valueOf(converObj.toString());
        } catch (Exception e) {
            return 0;
        }
    }


    /**
     * 转换为 float，异常时返回0
     *
     * @param converObj
     *
     * @return
     */
    public static float toFloat(Object converObj) {
        try {
            return Float.valueOf(converObj.toString());
        } catch (Exception e) {
            return 0;
        }
    }
}
