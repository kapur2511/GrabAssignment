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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.grab.grabtest.R;
import com.grab.grabtest.mvp.presenter.BasePresenter;
import com.grab.grabtest.mvp.view.adapters.BaseListAdapter;
import com.grab.grabtest.mvp.view.renderer.BaseRenderer;
import com.grab.grabtest.mvp.view.viewmodel.ListItem;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

public abstract class BaseFragment<P extends BasePresenter, V extends BaseRenderer, A extends BaseListAdapter> extends Fragment {

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        Log.i("LifeCycle","onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("LifeCycle","onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("LifeCycle","onCreateView");
        View fragmentView = inflater.inflate(layoutId, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        setupRecyclerView(getRecyclerView(), getAdapter());
        setupPagination(getRecyclerView());
        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("LifeCycle","onStart");
        setupPresenter();
        switchUI(false, true, false);
        loadData(presenter);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    protected void reloadData(){
        loadData(presenter);
    }

    protected void setupRecyclerView(RecyclerView recyclerView, A adapter){
        this.adapter = adapter;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.setLoading(true);
        recyclerView.setAdapter(adapter);
    }

    private void setupPresenter(){
        if(presenter!=null){
            presenter.init(this);
        }
    }

    protected void switchUI(boolean contentUI, boolean loadingUI, boolean errorUI){
        if(loadingUI)
            startLoadingAnimation();
        else
            stopLoadingAnimation();
        constraintLayoutLoading.setVisibility(loadingUI ? View.VISIBLE : View.GONE);
        linearLayoutContent.setVisibility(contentUI ? View.VISIBLE : View.GONE);
        constraintLayoutError.setVisibility(errorUI ? View.VISIBLE : View.GONE);
    }

    protected void startLoadingAnimation(){
        imgLoading.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_animation));
    }

    protected void stopLoadingAnimation(){
        imgLoading.clearAnimation();
    }

    protected List<ListItem> getCachedListData(String key, Type type){
        Gson gson = new Gson();
        String json = sharedPreferences.getString(key, "");
        if(!json.isEmpty()){
            List<ListItem> listItems = gson.fromJson(json, type);
            Log.d("CACHED DATA: ", listItems.toString());
            return listItems;
        }else
            return null;
    }

    @OnClick(R.id.btn_error)
    void onRetry(View view){
        switchUI(false, true, false);
        loadData(presenter);
    }

    protected abstract void setupPagination(RecyclerView recyclerView);

    protected abstract void loadData(P presenter);

    protected abstract RecyclerView getRecyclerView();

    protected abstract A getAdapter();

    protected final String TAG = this.getClass().getSimpleName();


    @Inject
    SharedPreferences sharedPreferences;

    @BindView(R.id.ll_content)
    LinearLayout linearLayoutContent;

    @BindView(R.id.cl_loading)
    ConstraintLayout constraintLayoutLoading;

    @BindView(R.id.cl_error)
    ConstraintLayout constraintLayoutError;

    @BindView(R.id.img_loading)
    ImageView imgLoading;

    @Inject
    P presenter;

    A adapter;

    private Unbinder unbinder;
    protected DisplayMetrics displayMetrics;
    protected int layoutId;
}
