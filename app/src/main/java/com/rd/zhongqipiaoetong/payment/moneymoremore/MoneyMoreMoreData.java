package com.rd.zhongqipiaoetong.payment.moneymoremore;

import android.util.Log;

import com.google.gson.Gson;
import com.money.more.basil.Conts;
import com.rd.zhongqipiaoetong.module.account.model.AppPaymentMo;
import com.rd.zhongqipiaoetong.payment.ConverFormat;
import com.rd.zhongqipiaoetong.utils.ref.RefException;
import com.rd.zhongqipiaoetong.utils.ref.RefObject;
import com.rd.zhongqipiaoetong.utils.ref.RefWrapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/19/16.
 */
public class MoneyMoreMoreData {
    /**
     * 根据PaymentData 返回指定 MoneyMoremore data
     *
     * @param data
     * @param paymentData
     * @param <T>
     *
     * @return
     *
     * @throws RefException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public static <T> T setPaymentData(Class<T> data, Map<String, Object> paymentData, int type) throws RefException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        T                       object  = data.newInstance();
        RefObject               refobj  = RefObject.wrap(object);
        HashMap<String, Method> methods = refobj.getPublicMethod();
        for (String key : paymentData.keySet()) {
            if (MoneyMoreMoreWrapper.isPrimitiveMethod(type, key)) {
                Method method = methods.get(MoneyMoreMoreWrapper.getValue(type, key));
                Object value  = paymentData.get(key);
                Log.d("method", key);
                setMethodValue(object, method, value);
            }
        }
        return object;
    }

    /**
     * 设置MoneyMoremore 信息（公钥、私钥、URL）
     *
     * @param mo
     */
    public static void setMoneyConfig(AppPaymentMo mo) {
        Gson             gson = new Gson();
        MoneyMoreMoreCfg cfg  = gson.fromJson(mo.extraData2Json(), MoneyMoreMoreCfg.class);
        //        if (cfg.isProduction()) {
        Conts.setServiceUrl(mo.getUrl());
        //        }
        Conts.setMddPrivateKey(cfg.getPrivatekey());
        Conts.setMddPublicKey(cfg.getPublickey());
    }

    /**
     * 执行invoke，参数类型校验
     *
     * @param object
     * @param method
     * @param valueObject
     *
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static void setMethodValue(Object object, Method method, Object valueObject) throws IllegalAccessException, InvocationTargetException {
        Class clazz = method.getParameterTypes()[0];
        if (RefWrapper.isPrimitiveWrapperClass(clazz)) {
            String value = String.valueOf(valueObject);
            if (clazz == int.class) {
                method.invoke(object, ConverFormat.toInt(value));
            } else if (clazz == long.class) {
                method.invoke(object, ConverFormat.toLong(value));
            } else if (clazz == float.class) {
                method.invoke(object, ConverFormat.toFloat(value));
            } else if (clazz == double.class) {
                method.invoke(object, ConverFormat.toDouble(value));
            } else if (clazz == boolean.class) {
                if (value.equals("1")) {
                    method.invoke(object, true);
                }
                method.invoke(object, false);
            } else {
                method.invoke(object, value);
            }
        }
    }
}
