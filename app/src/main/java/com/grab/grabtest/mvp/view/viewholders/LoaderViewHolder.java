/*
 * Copyright (C) 23-May-2019 Cricbuzz.com
 * All rights reserved.
 *
 * http://www.cricbuzz.com
 * @author: kshitiz.kapur
 */

package com.grab.grabtest.mvp.view.viewholders;

import android.support.annotation.NonNull;
import android.view.View;

import com.grab.grabtest.mvp.view.viewmodel.ListItem;

public class LoaderViewHolder<H extends ListItem> extends BaseViewHolder<H>{

    public LoaderViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void setData(ListItem data) {

    }
}
