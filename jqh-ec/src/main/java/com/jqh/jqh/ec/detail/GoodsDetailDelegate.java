package com.jqh.jqh.ec.detail;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.joanzapata.iconify.widget.IconTextView;
import com.jqh.jqh.deletegates.JqhDelegate;
import com.jqh.jqh.ec.R;
import com.jqh.jqh.net.RestClient;
import com.jqh.jqh.net.calback.ISuccess;
import com.jqh.jqh.ui.banner.HolderCreate;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class GoodsDetailDelegate extends JqhDelegate implements
        AppBarLayout.OnOffsetChangedListener{

    Toolbar mToolbar = null;
    TabLayout mTabLayout = null;
    ViewPager mViewPager = null;
    ConvenientBanner<String> mBanner = null;

    CollapsingToolbarLayout mCollapsingToolbarLayout = null;

    AppBarLayout mAppBar = null;

    //底部
    IconTextView mIconFavor = null;
    com.jqh.jqh.widget.CircleTextView mCircleTextView = null;
    RelativeLayout mRlAddShopCart = null;
    IconTextView mIconShopCart = null;

    private static final String ARG_GOODS_ID = "ARG_GOODS_ID";
    private int mGoodsId = -1;

    private String mGoodsThumbUrl = null;
    private int mShopCount = 0;

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate()
            .override(100, 100);

    public static  GoodsDetailDelegate create(int goodsId){
        final Bundle args = new Bundle();
        args.putInt(ARG_GOODS_ID, goodsId);
        final GoodsDetailDelegate delegate = new GoodsDetailDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mGoodsId = args.getInt(ARG_GOODS_ID);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_goods_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {


        mToolbar = (Toolbar)rootView.findViewById(R.id.goods_detail_toolbar);
        mTabLayout = (TabLayout)rootView.findViewById(R.id.tab_layout);

        mViewPager = (ViewPager)rootView.findViewById(R.id.view_pager);
        mBanner = (ConvenientBanner<String>)rootView.findViewById(R.id.detail_banner);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout)rootView.findViewById(R.id.collapsing_toolbar_detail);
        mAppBar = (AppBarLayout)rootView.findViewById(R.id.app_bar_detail);

        //底部
        mIconFavor = (IconTextView)rootView.findViewById(R.id.icon_favor);
        mCircleTextView = (com.jqh.jqh.widget.CircleTextView)rootView.findViewById(R.id.tv_shopping_cart_amount);
        mRlAddShopCart = (RelativeLayout)rootView.findViewById(R.id.rl_add_shop_cart);
        mIconShopCart = (IconTextView)rootView.findViewById(R.id.icon_shop_cart);

        // 伸缩topbar变换颜色
        mCollapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(getContext(),R.color.holo_orange_dark));
        mAppBar.addOnOffsetChangedListener(this);

        initData();
        initTabLayout();

    }

    private void initData(){
        RestClient.builder()
                .url("goodsdetail")
                .params("goods_id",mGoodsId)
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject data =
                                JSON.parseObject(response).getJSONObject("data");
                        initBanner(data);
                        initGoodsInfo(data);
                        initPager(data);
                    }
                }).build().get();
    }

    private void initBanner(JSONObject data){
        final JSONArray array = data.getJSONArray("banners");
        final List<String> images = new ArrayList<>();
        final int size = array.size();
        for(int i = 0 ; i < size ; i++){
            images.add(array.getString(i));
        }
        mBanner.setPages(new HolderCreate(),images)
                .setPageIndicator(new int[]{R.drawable.dot_normal,R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPageTransformer(new DefaultTransformer())
                .startTurning(3000)
                .setCanLoop(true);
    }

    private void initGoodsInfo(JSONObject data){
        final String goodsData = data.toJSONString();
        getSupportDelegate().loadRootFragment(R.id.frame_goods_info,GoodsInfoDelegate.create(goodsData));
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(),R.color.app_name));
        mTabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
        mTabLayout.setBackgroundColor(Color.WHITE);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    private void initTabLayout(){
        // 均分
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setSelectedTabIndicatorColor
                (ContextCompat.getColor(getContext(), R.color.app_name));
        mTabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
        mTabLayout.setBackgroundColor(Color.WHITE);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initPager(JSONObject data) {
        final PagerAdapter adapter = new TabPagerAdapter(getFragmentManager(), data);
        mViewPager.setAdapter(adapter);
    }

    //  水平动画进入
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

    }
}
