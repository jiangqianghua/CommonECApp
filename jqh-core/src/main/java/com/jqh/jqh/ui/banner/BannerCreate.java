package com.jqh.jqh.ui.banner;

import android.widget.AdapterView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.jqh.jqh.R;

import java.util.ArrayList;

public class BannerCreate {

    public static  void setDefalut(ConvenientBanner<String> convenientBanner,
                                   ArrayList<String> banners,
                                   OnItemClickListener clickListener){
        convenientBanner.setPages(new HolderCreate(),banners)
                .setPageIndicator(new int[]{R.drawable.dot_normal,R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(clickListener)
                .startTurning(3000)
                .setCanLoop(true);
    }
}
