package com.jude.fishing.model.service;


import com.jude.fishing.BuildConfig;
import com.jude.fishing.config.Service;
import com.jude.utils.JUtils;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by Mr.Jude on 2015/8/24.
 * 服务器连接客户端
 */
public class ServiceClient {
    private static Service mService;
    private static OkHttpClient okHttpClient;

    public static OkHttpClient getOkHttpClient(){
        if (okHttpClient==null){
            okHttpClient = new OkHttpClient();
        }
        return okHttpClient;
    }
    public static Service getService(){
        if (mService == null){
            createService();
        }
        return mService;
    }

    private static void createService(){
        mService = createAdapter().create(Service.class);
    }

    private static RestAdapter createAdapter(){
        boolean isInDebugServer = JUtils.getSharedPreference().getBoolean("DebugServer",false);
        return new RestAdapter.Builder()
                .setEndpoint(isInDebugServer?Service.BASE_URL_DEBUG:Service.BASE_URL)
                .setRequestInterceptor(new HeaderInterceptors())
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .setConverter(new WrapperConverter())
                .setClient(new OkClient(getOkHttpClient()))
                .setRequestInterceptor(new HeaderInterceptors())
                .build();
    }
}
