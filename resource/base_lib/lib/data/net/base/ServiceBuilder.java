package com.dream.android.sample.lib.data.net.base;

import com.google.gson.*;
import com.dream.android.sample.lib.BaseApplication;
import com.dream.android.sample.lib.executor.JobExecutor;
import com.dream.android.sample.lib.utils.ApplicationUtil;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Type;
import java.util.Date;

/**
 * Description: Network servers builder
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/5/27
 */
@Singleton
public class ServiceBuilder {

    public static String SERVER_BASE_URL;

    private Retrofit restAdapter;

    @Inject
    public ServiceBuilder(JobExecutor executor) {
        SERVER_BASE_URL = ApplicationUtil.getMetaValue(BaseApplication.getInstance(), "SERVER_URL");
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        restAdapter = new Retrofit.Builder()
                .baseUrl(SERVER_BASE_URL) //服务器通信地址设置
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().registerTypeAdapter(Date.class, new DateSerializerUtil()).create()))
                .client(new OkHttpClient.Builder().addInterceptor(logging).build())
//                .client(new OkHttpClient.Builder().addInterceptor(new Interceptor() { //interceptor form Request and Response
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        Request request = chain.request();
//                        return chain.proceed(request.newBuilder().header("X-Auth-Token", "hello").build());
//                    }
//                }).build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.from(executor)))
                .build();
    }

    public <T> T build(Class<T> clazz) {
        return restAdapter.create(clazz);
    }

    public static class DateSerializerUtil implements JsonSerializer<Date>, JsonDeserializer<Date> {

        @Override
        public JsonElement serialize(Date date, Type type,
                                     JsonSerializationContext context) {
            return new JsonPrimitive(date.getTime());
        }

        @Override
        public Date deserialize(JsonElement element, Type type, JsonDeserializationContext context)
                throws JsonParseException {
            return new Date(element.getAsJsonPrimitive().getAsLong());
        }
    }
}
