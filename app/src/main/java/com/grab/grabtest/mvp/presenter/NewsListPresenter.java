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

package com.grab.grabtest.mvp.presenter;

import android.util.Log;

import com.grab.grabtest.api.NewsAPI;
import com.grab.grabtest.mvp.model.ArticlesModel;
import com.grab.grabtest.mvp.model.NewsListModel;
import com.grab.grabtest.mvp.view.renderer.NewsListRenderer;
import com.grab.grabtest.mvp.view.viewmodel.NewsListViewModel;
import com.grab.grabtest.mvp.view.viewmodel.NewsViewModel;
import com.grab.grabtest.utils.Constants;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class NewsListPresenter extends BasePresenter<NewsListRenderer> {

    @Inject
    public NewsListPresenter() {
    }

    @Override
    public void loadData(Map<String, Object> stringObjectHashMap) {
        Log.d(TAG,"Loading News");
        newsAPI.loadNews(stringObjectHashMap)
                .subscribeOn(Schedulers.io())
                .compose(new NewsListTransformer())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NewsListObserver());
    }

    private class NewsListTransformer implements ObservableTransformer<Response<NewsListModel>, NewsListViewModel>{

        @Override
        public ObservableSource<NewsListViewModel> apply(Observable<Response<NewsListModel>> responseObservable) {
            return responseObservable.filter(new Predicate<Response<NewsListModel>>() {
                @Override
                public boolean test(Response<NewsListModel> newsListModelResponse) throws Exception {
                    Log.d(TAG, "========="+newsListModelResponse.message());
                    return newsListModelResponse!=null && newsListModelResponse.body()!=null && newsListModelResponse.body().getArticles()!=null && newsListModelResponse.body().getArticles().size()>0;
                }
            }).flatMap(new Function<Response<NewsListModel>, ObservableSource<NewsListModel>>() {
                @Override
                public ObservableSource<NewsListModel> apply(Response<NewsListModel> newsListModelResponse) throws Exception {
                    Log.d(TAG, "========="+newsListModelResponse.body());
                    return Observable.just(newsListModelResponse.body());
                }
            }).flatMapIterable(new Function<NewsListModel, Iterable<ArticlesModel>>() {
                @Override
                public Iterable<ArticlesModel> apply(NewsListModel newsListModel) throws Exception {
                    totalResults = newsListModel.getTotalResults();
                    if(totalPages == 0)
                        totalPages   = (totalResults%Constants.RESPONSE_PER_PAGE) == 0 ?
                                    (totalResults/Constants.RESPONSE_PER_PAGE) : ((totalResults/Constants.RESPONSE_PER_PAGE)+1);
                    return newsListModel.getArticles();
                }
            }).flatMap(new Function<ArticlesModel, ObservableSource<NewsViewModel>>() {
                @Override
                public ObservableSource<NewsViewModel> apply(ArticlesModel articlesModel) throws Exception {
                    return Observable.just(new NewsViewModel(articlesModel));
                }
            }).toList().flatMapObservable(new Function<List<NewsViewModel>, ObservableSource<NewsListViewModel>>() {
                @Override
                public ObservableSource<NewsListViewModel> apply(List<NewsViewModel> newsViewModels) throws Exception {
                    NewsListViewModel newsListViewModel = new NewsListViewModel();
                    newsListViewModel.setNewsViewModels(newsViewModels);
                    newsListViewModel.setTotalResult(totalResults);
                    return Observable.just(newsListViewModel);
                }
            });
        }
    }


    private class NewsListObserver implements Observer<NewsListViewModel>{


        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(NewsListViewModel newsListViewModel) {
            Log.d("RESPONSE: ",newsListViewModel.toString());
            view.renderNewsList(newsListViewModel);
        }

        @Override
        public void onError(Throwable e) {
            view.renderError();
        }

        @Override
        public void onComplete() {

        }
    }

    public int getTotalPages() {
        return totalPages;
    }

    private int totalResults, totalPages;

    @Inject
    NewsAPI newsAPI;
}
