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

package com.grab.grabtest.mvp.view.adapters;

import android.util.Log;
import android.view.View;
import com.grab.grabtest.R;
import com.grab.grabtest.mvp.view.viewholders.BaseViewHolder;
import com.grab.grabtest.mvp.view.viewholders.LoaderViewHolder;
import com.grab.grabtest.mvp.view.viewholders.NewsListItemViewHolder;
import com.grab.grabtest.mvp.view.viewmodel.ListItem;
import com.grab.grabtest.utils.ClickListener;

import java.util.List;

public class NewsListAdapter<H extends ListItem> extends BaseListAdapter<H>{

    public NewsListAdapter(List<H> list, ClickListener clickListener) {
        super(list);
        this.clickListener = clickListener;
        viewMap.put(VIEW_TYPE_NEWS,  R.layout.view_news_item);
        viewMap.put(VIEW_TYPE_LOADER, R.layout.view_loader_item);
    }

    @Override
    public int getItemViewType(int position) {
        if(position == list.size()-1 && loading)
            return VIEW_TYPE_LOADER;
        else if(list.size() != 0)
            return VIEW_TYPE_NEWS;
        else
            return -1;
    }

    @Override
    protected BaseViewHolder<H> callViewHolder(View view, int viewType) {
        switch (viewType){
            case VIEW_TYPE_NEWS:
                return new NewsListItemViewHolder(view, clickListener);
            case VIEW_TYPE_LOADER:
                return new LoaderViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    protected void onBind(BaseViewHolder<H> viewHolder, int position) {
        Log.d("VIEW_HOLDER: ",viewHolder.getClass().getSimpleName());
        Log.d("VIEW_HOLDER POSITION: ",""+position);
        Log.d("VIEW_HOLDER DATA: ",""+list.get(position));
        if(position<list.size()-1)
            viewHolder.setData(list.get(position));
    }

    @Override
    protected int getLayoutId(int viewType) {
        if(viewMap.containsKey(viewType))
            return viewMap.get(viewType);
        else
            throw new RuntimeException();
    }

    private ClickListener clickListener;
    private final int VIEW_TYPE_NEWS  = 1;
    private final int VIEW_TYPE_LOADER = 2;
}
