/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rd.zhongqipiaoetong.network.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.rd.zhongqipiaoetong.common.BaseParams;
import com.rd.zhongqipiaoetong.network.NetworkUtil;
import com.rd.zhongqipiaoetong.network.ResponseParams;
import com.rd.zhongqipiaoetong.network.entity.HttpResult;
import com.rd.zhongqipiaoetong.network.exception.ApiException;
import com.rd.zhongqipiaoetong.network.exception.AppResultCode;
import com.rd.zhongqipiaoetong.utils.Utils;
import com.rd.zhongqipiaoetong.utils.log.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/6 9:35
 * <p/>
 * Description:  JSON response 解析
 */
final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson           gson;
    private final TypeAdapter<T> adapter;
    private final Type           type;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter, Type type) {
        this.gson = gson;
        this.adapter = adapter;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        NetworkUtil.dismissCutscenes();
        String response = value.string().trim();
        if (BaseParams.IS_DEBUG)
            Logger.w("RDClient", "response >> \n" + response);

        // 解析resCode和resMsg
        HttpResult httpResult = gson.fromJson(response, HttpResult.class);
        if (httpResult.getResCode() != AppResultCode.SUCCESS && !httpResult.getRes_code().equals(AppResultCode.SUCCESS_9999) && httpResult.getResCode() !=
                AppResultCode.SUCCESS9999) {
            throw new ApiException(httpResult.getResCode(), httpResult.getResMsg());
        }

        //如果请求返回类是HttpResult 那么直接返回
        if (type == HttpResult.class) {
            httpResult.setBody(response);
            return (T) httpResult;
        }

        try {
            String str = new JSONObject(response).getString(ResponseParams.RES_DATA);
            // 判断需要解析成的T对象是否是Collection的实现类,是则截取成array类型的JSON字符串
            if (Utils.isInstanceOfCollection(adapter.getClass())) {
                str = str.substring(str.indexOf("["), str.length() - 1);
            }

            StringReader reader     = new StringReader(str);
            JsonReader   jsonReader = gson.newJsonReader(reader);
            return adapter.read(jsonReader);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return null;
        } finally {
            value.close();
        }
    }
}