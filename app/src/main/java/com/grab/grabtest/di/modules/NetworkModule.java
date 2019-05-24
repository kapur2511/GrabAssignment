/*
 * Copyright (C) 23-May-2019 Cricbuzz.com
 * All rights reserved.
 *
 * http://www.cricbuzz.com
 * @author: kshitiz.kapur
 */

/*
 * @author: kshitiz.kapur
 */

package com.grab.grabtest.di.modules;


import com.grab.grabtest.api.NewsAPI;
import com.grab.grabtest.networking.RetrofitInterceptor;
import com.grab.grabtest.utils.Constants;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    Retrofit provideRetrofitClient(RetrofitInterceptor retrofitInterceptor, File cacheFile) {

        Cache cache = new Cache(cacheFile, 10 * 1024 * 1024);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(retrofitInterceptor)
                .cache(cache)
                .build();


        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    RetrofitInterceptor provideInterceptor(){
        return new RetrofitInterceptor();
    }

    @Provides
    @Singleton
    NewsAPI provideWeatherService(Retrofit retrofit){
        return retrofit.create(NewsAPI.class);
    }
}
