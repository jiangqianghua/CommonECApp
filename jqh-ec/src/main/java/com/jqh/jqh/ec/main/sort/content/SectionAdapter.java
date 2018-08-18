package com.jqh.jqh.ec.main.sort.content;

import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jqh.jqh.ec.R;

import java.util.List;

import retrofit2.http.OPTIONS;

public class SectionAdapter extends BaseSectionQuickAdapter<SectionBean,BaseViewHolder> {

    public static final RequestOptions RECYCLER_OPTIONS =
            new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate();
    public SectionAdapter(int layoutResId, int sectionHeadResId, List<SectionBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SectionBean item) {
        // 对头数据转化
        helper.setText(R.id.section_header,item.header);
        helper.setVisible(R.id.section_more,item.ismIsMore());
        helper.addOnClickListener(R.id.section_more);
    }

    @Override
    protected void convert(BaseViewHolder helper, SectionBean item) {
        // 对商品的转化
        final String thumb = item.t.getGoodsThumb(); //t 是我们传入的泛型
        final String name = item.t.getGoodsName();
        final int goodsId = item.t.getGoodsId();
        final SectionContentItemEntity entity = item.t;
        helper.setText(R.id.tv,name);
        final AppCompatImageView goodsImageView = helper.getView(R.id.iv);
        Glide.with(mContext)
                .load(thumb)
                .apply(RECYCLER_OPTIONS)
                .into(goodsImageView);


    }
}
