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
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grab.grabtest.R;
import com.grab.grabtest.mvp.presenter.NewsListPresenter;
import com.grab.grabtest.mvp.view.adapters.BaseListAdapter;
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

import javax.inject.Inject;

import butterknife.BindView;

public class NewsListFragment extends BaseFragment<NewsListPresenter, NewsListRenderer, NewsListAdapter> implements NewsListRenderer, ClickListener {

    public NewsListFragment(){
        layoutId = R.layout.fragment_news_list;
    }


    @Override
    protected void loadData(NewsListPresenter presenter) {
        long lastFetchTime = sharedPreferences.getLong(Constants.PREF_CURRENT_TIME,0);
        if(CommonUtils.isConnected(getContext()) && adapter!=null && adapter.getItemCount()==0){
            stringObjectHashMap.put("country","in");
            if(getContext()!=null){
                TelephonyManager manager = (TelephonyManager)getContext().getSystemService(Context.TELEPHONY_SERVICE);
                String countryCode=manager.getSimCountryIso();
                Log.d(TAG,"countryCode: "+countryCode);
                stringObjectHashMap.put("country","in");
            }
            stringObjectHashMap.put("apiKey", Constants.API_KEY);
            if(stringObjectHashMap.containsKey("page"))
                stringObjectHashMap.remove("page");
            if((System.currentTimeMillis() - lastFetchTime) > 300000)
                presenter.loadData(stringObjectHashMap);
            else{
                List<ListItem> listItems = getCachedListData(Constants.PREF_CACHE_DATA, new TypeToken<List<NewsViewModel>>(){}.getType());
                currentPage   = sharedPreferences.getInt(Constants.PREF_CURRENT_PAGE, 1);
                nextPageToken = sharedPreferences.getInt(Constants.PREF_NEXT_PAGE_TOKEN,currentPage+1);
                totalPages    = sharedPreferences.getInt(Constants.PREF_TOTAL_PAGES, 0);
                if(listItems!=null){
                    adapter.addItems(listItems);
                    switchUI(true, false, false);
                }
                else
                    switchUI(false, false, true);
            }
        }else if(adapter!=null && adapter.getItemCount()>0){
            switchUI(true, false, false);
        }else{

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
        BaseListAdapter baseListAdapter = (BaseListAdapter) recyclerViewNewsList.getAdapter();
        baseListAdapter.addItems(newsListViewModel.getNewsViewModels());
        if(currentPage<presenter.getTotalPages())
            baseListAdapter.addLoadingItem();
        else
            baseListAdapter.removeLoadingItem();
        switchUI(true, false, false);
        sharedPreferences.edit().putLong(Constants.PREF_CURRENT_TIME, System.currentTimeMillis()).apply();
        Gson gson = new Gson();
        String json = gson.toJson(adapter.getList());
        Log.d(TAG,"SAVING DATA: "+json);
        sharedPreferences.edit().putString(Constants.PREF_CACHE_DATA, json).apply();
        nextPageToken = currentPage++;
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
                    stringObjectHashMap.put("pageToken", nextPageToken);
                    isLoading = true;
                    presenter.loadData(stringObjectHashMap);
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

    }

    @BindView(R.id.rv_list)
    RecyclerView recyclerViewNewsList;

    @Inject
    FusedLocationProviderClient fusedLocationClient;

    private Map<String, Object> stringObjectHashMap = new HashMap<>();
    private boolean isLastPage, isLoading;
    private int currentPage = 1, totalPages, nextPageToken;
    private String cityName;
    private AlertDialog alertDialog;
    private final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
}
