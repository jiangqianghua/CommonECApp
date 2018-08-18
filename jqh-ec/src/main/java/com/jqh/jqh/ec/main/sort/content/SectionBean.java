package com.jqh.jqh.ec.main.sort.content;

import com.chad.library.adapter.base.entity.SectionEntity;

public class SectionBean extends SectionEntity<SectionContentItemEntity> {

    //是否显示更多
    private boolean mIsMore = false;

    private int mId = -1 ;

    public SectionBean(SectionContentItemEntity sectionContentItemEntity) {
        super(sectionContentItemEntity);
    }

    // isHeader 是否是标题，
    public SectionBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public boolean ismIsMore() {
        return mIsMore;
    }

    public void setmIsMore(boolean mIsMore) {
        this.mIsMore = mIsMore;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }
}
