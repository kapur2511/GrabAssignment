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

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grab.grabtest.mvp.view.viewholders.BaseViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseListAdapter<H> extends RecyclerView.Adapter<BaseViewHolder<H>> {

    BaseListAdapter(List<H> list){
        this.list = list;
    }

    @NonNull
    @Override
    public BaseViewHolder<H> onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        try {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(getLayoutId(i), viewGroup,false);
            return callViewHolder(view, i);
        }catch (RuntimeException e){
            e.printStackTrace();
            Log.e("ERROR: ","NO VIEW TYPE FOUND");
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<H> hBaseViewHolder, int i) {
        onBind(hBaseViewHolder, i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setLoading(boolean loading){
        this.loading = loading;
    }

    public void addLoadingItem(){
        list.add(null);
        notifyItemInserted(list.size()-1);
    }

    public void addItems(List<H> list){
        if(this.list!=null && this.list.size()==0){
            this.list = list;
            notifyItemRangeInserted(0, list.size());
        }
        else if(this.list!=null){
            Log.d("LIST LAST: ","==="+this.list.get(this.list.size()-1));
            removeLoadingItem();
            int startPos = this.list.size();
            this.list.addAll(startPos,list);
            notifyItemRangeInserted(startPos, list.size());
            Log.d("LIST: ",this.list.toString());
        }
    }

    public List<H> getList(){
        return this.list;
    }

    public void removeLoadingItem(){
        Log.d("LIST REMOVE LOADER: ",this.list.toString());
        if(list.get(list.size()-1) == null){
            list.remove(list.size()-1);
            notifyItemRemoved(list.size()-1);
        }
    }

    protected abstract int getLayoutId(int viewType) throws RuntimeException;

    protected abstract BaseViewHolder<H> callViewHolder(View view, int viewType);

    protected abstract void onBind(BaseViewHolder<H> viewHolder, int position);

    protected Map<Integer, Integer> viewMap = new HashMap<>();
    protected boolean loading;
    protected List<H> list;
}
