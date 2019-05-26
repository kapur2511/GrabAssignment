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

package com.grab.grabtest.mvp.view.viewholders;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.grab.grabtest.R;
import com.grab.grabtest.mvp.view.viewmodel.ListItem;
import com.grab.grabtest.mvp.view.viewmodel.NewsViewModel;
import com.grab.grabtest.utils.ClickListener;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

public class NewsListItemViewHolder<H extends ListItem> extends BaseViewHolder<ListItem> implements View.OnClickListener{

    public NewsListItemViewHolder(@NonNull View itemView, ClickListener clickListener) {
        super(itemView);
        this.clickListener = clickListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void setData(ListItem data) {
        this.listItem = data;
        NewsViewModel newsViewModel = (NewsViewModel) data;
        if(newsViewModel.getUrlToImage()!=null && !newsViewModel.getUrlToImage().isEmpty())
            Picasso.get().load(newsViewModel.getUrlToImage()).fit().into(imageViewThumbnail);
        Log.d("NEWS: ",newsViewModel.toString());
        textViewTitle.setText(newsViewModel.getTitle());
        textViewDescription.setText(newsViewModel.getDescription());
    }

    @Override
    public void onClick(View v) {
        clickListener.onClick(listItem);
    }

    private ClickListener clickListener;
    private ListItem listItem;

    @BindView(R.id.img_news)
    ImageView imageViewThumbnail;

    @BindView(R.id.txt_title)
    TextView textViewTitle;

    @BindView(R.id.txt_description)
    TextView textViewDescription;

}
