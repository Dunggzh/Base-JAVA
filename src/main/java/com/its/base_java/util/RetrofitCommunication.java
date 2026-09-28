package com.its.base_java.util;

import com.its.base_java.dto.config.ConnectionConfig;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.*;

public class RetrofitCommunication {

    public static <T> T buildSetting(Class<T> classType, ConnectionConfig connect) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(connect.getApiUrl())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(buildCommunication(connect))
                .build();

        return retrofit.create(classType);
    }

    private static OkHttpClient buildCommunication(ConnectionConfig connect) {
        final int maxRequest = 100;
        final int maxRequestPerHost = 10;
        final int maxIdle = 256;
        final long connectTimeout = 30;
        final long keepAliveDuration = 60;

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(maxRequest);
        dispatcher.setMaxRequestsPerHost(maxRequestPerHost);
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .dispatcher(dispatcher)
                .addInterceptor(logger)
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(connectTimeout, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(maxIdle, keepAliveDuration, TimeUnit.SECONDS))
                .build();
    }
}
