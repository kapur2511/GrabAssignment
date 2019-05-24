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

package com.grab.grabtest.api;

import com.grab.grabtest.mvp.model.NewsListModel;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface NewsAPI {

    //News API
    @GET("top-headlines")
    Observable<Response<NewsListModel>> loadNews(@QueryMap Map<String, Object> params);
}
