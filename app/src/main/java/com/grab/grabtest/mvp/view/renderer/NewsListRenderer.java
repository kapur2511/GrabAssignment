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

package com.grab.grabtest.mvp.view.renderer;

import com.grab.grabtest.mvp.view.viewmodel.NewsListViewModel;

public interface NewsListRenderer extends BaseRenderer{

    void renderNewsList(NewsListViewModel newsListViewModel);

    void renderError();
}
