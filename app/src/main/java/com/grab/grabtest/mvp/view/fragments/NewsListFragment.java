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

package com.grab.grabtest.mvp.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v4.os.ConfigurationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grab.grabtest.R;
import com.grab.grabtest.mvp.presenter.NewsListPresenter;
import com.grab.grabtest.mvp.view.activities.WebViewActivity;
import com.grab.grabtest.mvp.view.adapters.NewsListAdapter;
import com.grab.grabtest.mvp.view.renderer.NewsListRenderer;
import com.grab.grabtest.mvp.view.viewmodel.ListItem;
import com.grab.grabtest.mvp.view.viewmodel.NewsListViewModel;
import com.grab.grabtest.mvp.view.viewmodel.NewsViewModel;
import com.grab.grabtest.utils.ClickListener;
import com.grab.grabtest.utils.CommonUtils;
import com.grab.grabtest.utils.Constants;
import com.grab.grabtest.utils.pagination.PaginationScrollListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.grab.grabtest.utils.Constants.ERROR;
import static com.grab.grabtest.utils.Constants.IMAGE_URL;
import static com.grab.grabtest.utils.Constants.NEWS_URL;
import static com.grab.grabtest.utils.Constants.NO_CACHE_AND_INTERNET;
import static com.grab.grabtest.utils.Constants.SHOULD_NOT_USE_CACHE;
import static com.grab.grabtest.utils.Constants.SHOULD_USE_CACHE;

public class NewsListFragment extends BaseFragment<NewsListPresenter, NewsListRenderer, NewsListAdapter> implements NewsListRenderer, ClickListener {

    public NewsListFragment(){
        layoutId = R.layout.fragment_news_list;
    }


    /**
     * Cached data has an expiry of 5 minutes. So after 5 minutes if we open the app it'll check if
     * internet is there and fetch the new data else use the cache.
     * */
    @Override
    protected void loadData(NewsListPresenter presenter) {
        long lastFetchTime = sharedPreferences.getLong(Constants.PREF_CURRENT_TIME,0);
        List<ListItem> listItems = getCachedListData(Constants.PREF_CACHE_DATA, new TypeToken<List<NewsViewModel>>(){}.getType());
        switch (CommonUtils.getCacheStatus(lastFetchTime, listItems, CommonUtils.isConnected(getContext()))){
            case SHOULD_USE_CACHE:
                currentPage   = sharedPreferences.getInt(Constants.PREF_CURRENT_PAGE, 1);
                nextPageToken = sharedPreferences.getInt(Constants.PREF_NEXT_PAGE_TOKEN,currentPage+1);
                totalPages    = sharedPreferences.getInt(Constants.PREF_TOTAL_PAGES, 0);
                if(listItems!=null && adapter!=null && adapter.getItemCount() == 0){
                    adapter.addItems(listItems);
                    adapter.setLoading(false);
                }

                switchUI(true, false, false);
                break;
            case SHOULD_NOT_USE_CACHE:
                stringObjectHashMap.put("country","in");
                if(getContext()!=null){
                    TelephonyManager manager = (TelephonyManager)getContext().getSystemService(Context.TELEPHONY_SERVICE);
                    String countryCode=manager.getSimCountryIso();
                    Log.d(TAG,"countryCode: "+ConfigurationCompat.getLocales(getContext().getResources().getConfiguration()).get(0).getCountry());
                    stringObjectHashMap.put("country",(countryCode==null || countryCode.isEmpty())? ConfigurationCompat.getLocales(getContext().getResources().getConfiguration()).get(0).getCountry() : countryCode);
                }
                stringObjectHashMap.put("apiKey", Constants.API_KEY);
                if(stringObjectHashMap.containsKey("page"))
                    stringObjectHashMap.remove("page");
                adapter.setLoading(true);
                presenter.loadData(stringObjectHashMap);
                break;
            case NO_CACHE_AND_INTERNET:
            case ERROR:
               default:
                   switchUI(false, false, true);
        }
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return recyclerViewNewsList;
    }

    @Override
    protected NewsListAdapter getAdapter() {
        adapter = new NewsListAdapter(new ArrayList(), this);
        return adapter;
    }


    @Override
    public void renderNewsList(NewsListViewModel newsListViewModel) {
        Log.d(TAG, newsListViewModel.toString());
        isLoading = false;
        adapter = (NewsListAdapter) recyclerViewNewsList.getAdapter();
        adapter.addItems(newsListViewModel.getNewsViewModels());
        if(currentPage<presenter.getTotalPages())
            adapter.addLoadingItem();
        else{
            adapter.removeLoadingItem();
            adapter.setLoading(false);
        }
        switchUI(true, false, false);
        sharedPreferences.edit().putLong(Constants.PREF_CURRENT_TIME, System.currentTimeMillis()).apply();
        Gson gson = new Gson();
        String json = gson.toJson(adapter.getList());
        Log.d(TAG,"SAVING DATA: "+json);
        sharedPreferences.edit().putString(Constants.PREF_CACHE_DATA, json).apply();
        nextPageToken = currentPage+1;
        sharedPreferences.edit().putInt(Constants.PREF_NEXT_PAGE_TOKEN, nextPageToken).apply();
        if(totalPages == 0){
            totalPages = presenter.getTotalPages();
            sharedPreferences.edit().putInt(Constants.PREF_TOTAL_PAGES, totalPages).apply();
        }
    }

    @Override
    public void renderError() {
        switchUI(false, false, true);
    }


    @Override
    protected void setupPagination(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new PaginationScrollListener((LinearLayoutManager) recyclerView.getLayoutManager()) {
            @Override
            protected void loadMoreItems() {
                if(currentPage<totalPages){
                    currentPage++;
                    sharedPreferences.edit().putInt(Constants.PREF_CURRENT_PAGE, currentPage).apply();
                    stringObjectHashMap.put("page", nextPageToken);
                    isLoading = true;
                    if(CommonUtils.isConnected(getContext()))
                        presenter.loadData(stringObjectHashMap);
                    else
                        adapter.removeLoadingItem();
                }else{
                    isLastPage = true;
                    adapter.setLoading(false);
                }
            }

            @Override
            public int getTotalPageCount() {
                return totalPages;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    @Override
    public void onClick(ListItem listItem) {
        if(listItem instanceof NewsViewModel){
            NewsViewModel newsViewModel = (NewsViewModel)listItem;
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtra(IMAGE_URL, newsViewModel.getUrlToImage());
            intent.putExtra(NEWS_URL, newsViewModel.getArticleUrl());
            startActivity(intent);
        }
    }

    @BindView(R.id.rv_list)
    RecyclerView recyclerViewNewsList;

    private Map<String, Object> stringObjectHashMap = new HashMap<>();
    private boolean isLastPage, isLoading;
    private int currentPage = 1, totalPages, nextPageToken;
}
