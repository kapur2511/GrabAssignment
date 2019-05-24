/*
 * Copyright (C) 24-May-2019 Cricbuzz.com
 * All rights reserved.
 *
 * http://www.cricbuzz.com
 * @author: kshitiz.kapur
 */

/*
 * @author: kshitiz.kapur
 */

package com.grab.grabtest;

import com.grab.grabtest.api.NewsAPI;
import com.grab.grabtest.mvp.model.NewsListModel;
import com.grab.grabtest.networking.RetrofitInterceptor;
import com.grab.grabtest.utils.Constants;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.observers.TestObserver;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@RunWith(JUnit4.class)
public class NewsListPresenterTest {


    @Before
    public void setup(){
        newsAPI = provideWeatherAPI();
    }

    @Test
    public void testWeatherRequest(){

        TestObserver<Response<NewsListModel>> responseTestSubscriber = TestObserver.create();
        Map<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("country","in");
        stringObjectHashMap.put("key", Constants.API_KEY);
        newsAPI.loadNews(stringObjectHashMap).subscribe(responseTestSubscriber);
        responseTestSubscriber.assertSubscribed();
        responseTestSubscriber.assertNoErrors();
        responseTestSubscriber.assertComplete();
        responseTestSubscriber.assertValueCount(1);
    }

    NewsAPI provideWeatherAPI() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new RetrofitInterceptor())
                .build();


        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(NewsAPI.class);
    }

    @After
    public void tearDown(){
        newsAPI = null;
    }

    NewsAPI newsAPI;
}
