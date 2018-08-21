package com.rd.zhongqipiaoetong.network;

import com.rd.zhongqipiaoetong.common.BaseParams;
import com.rd.zhongqipiaoetong.network.converter.GsonConverterFactory;
import com.rd.zhongqipiaoetong.network.interceptor.FridayParamsInject;
import com.rd.zhongqipiaoetong.network.interceptor.HttpLoggingInterceptor;

import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/5 10:30
 * <p/>
 * Description: 网络请求client
 */
public class FridayClient {
    // 网络请求超时时间值(s)
    private static final int    DEFAULT_TIMEOUT = 60;
    // retrofit实例
    private Retrofit retrofit;
    private FridayParamsInject inject;

    /**
     * 私有化构造方法
     */
    private FridayClient() {
        updataRetrofit();
    }

    /**
     * 调用单例对象
     */
    private static FridayClient instance;

    public static FridayClient getInstance() {
        if (instance == null)
            instance = new FridayClient();

        return instance;
    }

    public void updataRetrofit() {
        inject = FridayParamsInject.getInstance();
        // 创建一个OkHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 设置网络请求超时时间
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        // 添加签名参数
        builder.addInterceptor(inject.getInterceptor());
        // 打印参数
        builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        retrofit = new Retrofit.Builder()
                .baseUrl(BaseParams.FRIDAY_ADDRESS)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getServiceMap().clear();
    }

    /**
     * 创建单例对象
     */
    private static class RDClientInstance {
        static FridayClient instance = new FridayClient();
    }

    public FridayParamsInject getInject(){
        return inject;
    }

    ///////////////////////////////////////////////////////////////////////////
    // service
    ///////////////////////////////////////////////////////////////////////////
    private static TreeMap<String, Object> serviceMap;

    private static TreeMap<String, Object> getServiceMap() {
        if (serviceMap == null)
            serviceMap = new TreeMap<>();
        return serviceMap;
    }

    /**
     * @return 指定service实例
     */
    public static <T> T getService(Class<T> clazz) {
        if (getServiceMap().containsKey(clazz.getSimpleName())) {
            return (T) getServiceMap().get(clazz.getSimpleName());
        }

        T service = FridayClient.getInstance().retrofit.create(clazz);
        getServiceMap().put(clazz.getSimpleName(), service);
        return service;
    }
}